# Get the json file for training
def train(url_json):
    import pandas as pd
    import numpy as np
    import tensorflow as tf
    import tensorflow_recommenders as tfrs
    import keras
    import json
    from urllib.request import urlopen
    from datetime import datetime
    from sklearn import preprocessing

    response = urlopen(url_json)

    data = json.load(response)
    data2 = pd.DataFrame(data)

    # Data preprocessing
    data2['user_id'] = data2['user_id'].astype(str)
    data2['category'] = data2['category'].astype(str)
    data2['history'] = data2['history'].astype(np.float32)

    # Create datasets
    dataset = tf.data.Dataset.from_tensor_slices((
        tf.cast(data2['user_id'].values.reshape(-1, 1), tf.string),
        tf.cast(data2['category'].values.reshape(-1, 1), tf.string),
        tf.cast(data2['history'].values.reshape(-1, 1), tf.float32)
    ))

    @tf.function
    def rename(x0, x1, x2):
        y = {}
        y["user_id"] = x0
        y['category'] = x1
        y['history'] = x2
        return y


    dataset = dataset.map(rename)

    # Fetch unique category and user_id
    category = data2.category.values
    userid = data2.user_id.values

    unique_category = np.unique(list(category))
    unique_userid = np.unique(list(userid))

    # Training to create new model
    class RankingModel(tf.keras.Model):
        def __init__(self):
            super().__init__()
            embedding_dimension = 10

            # Compute embeddings for users
            self.userid_embeddings = tf.keras.Sequential([
                tf.keras.layers.experimental.preprocessing.StringLookup(
                    vocabulary=unique_userid, mask_token=None),
                tf.keras.layers.Embedding(len(unique_userid) + 1, embedding_dimension)
            ])

            # Compute embeddings for category
            self.category_embeddings = tf.keras.Sequential([
                tf.keras.layers.experimental.preprocessing.StringLookup(
                    vocabulary=unique_category, mask_token=None),
                tf.keras.layers.Embedding(len(unique_category) + 1, embedding_dimension)
            ])

            # Compute predictions
            self.ratings = tf.keras.Sequential([
                tf.keras.layers.Dense(64, activation="relu"),
                tf.keras.layers.Dense(32, activation="relu"),
                tf.keras.layers.Dense(1)
            ])


        def call(self, inputs):
            user_id, category = inputs
            userid_embedding = self.userid_embeddings(user_id)
            category_embedding = self.category_embeddings(category)

            return self.ratings(tf.concat([userid_embedding, category_embedding], axis=1))

    class userModel(tfrs.models.Model):
        def __init__(self):
            super().__init__()
            self.ranking_model: tf.keras.Model = RankingModel()
            self.task: tf.keras.layers.Layer = tfrs.tasks.Ranking(
                loss=tf.keras.losses.MeanSquaredError(),
                metrics=[tf.keras.metrics.RootMeanSquaredError()]
            )

        def compute_loss(self, features, training=False) -> tf.Tensor:
            user_id_embedded = self.ranking_model.userid_embeddings(features["user_id"])
            category_embedded = self.ranking_model.category_embeddings(features["category"])

            mission_predictions = self.ranking_model.ratings(tf.concat([user_id_embedded, category_embedded], axis=1))

            # Compute the loss and metrics
            return self.task(labels=features["history"], predictions=mission_predictions)

    model = userModel()
    model.compile(optimizer=tf.keras.optimizers.Adagrad(learning_rate=0.1))

    # Cache the dataset
    cache_dataset = dataset.cache()

    # Tensorboard
    logdir = "logs/scalars/" + datetime.now().strftime("%Y%m%d-%H%M%S")
    tensorboard_callback = keras.callbacks.TensorBoard(log_dir=logdir)

    # Training
    model.fit(cache_dataset, epochs=300, verbose=1, callbacks=[tensorboard_callback])
    
    # Save model
    tf.saved_model.save(model, "saved_model")
