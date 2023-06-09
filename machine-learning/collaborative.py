def predict(url_json,id):
    import pandas as pd
    import numpy as np
    import tensorflow as tf
    import tensorflow_recommenders as tfrs
    import keras
    import json
    import os
    from datetime import datetime
    from sklearn import preprocessing
    from urllib.request import urlopen
    
    
    current_dir = os.path.dirname(os.path.abspath(__file__))
    key_path = os.path.join(current_dir, 'saved_model')
    
    # Open saved_model file
    model= tf.saved_model.load(key_path)

    # Open json file by url_json
    data = url_json
    dataframe = pd.DataFrame(data)
    dataframe['category'] = dataframe.category.astype(np.str)
    unique_category = np.unique(list(dataframe['category']))

    # Create array with user id
    user = np.array(["{}".format(id) for i in range(len(unique_category))])

    # Convert user to tf.data.Dataset
    test_data = tf.data.Dataset.from_tensor_slices((tf.cast(user.reshape(-1,1), tf.string),
                                                    tf.cast(unique_category.reshape(-1,1), tf.string)))

    # Name the columns 
    @tf.function
    def rename_test(x0,x1):
        y = {}
        y["user_id"] = x0
        y['category'] = x1
        return y

    test_data = test_data.map(rename_test)

    # Make predictions and store them in to dictionary
    test_mission = {}
    for b in test_data:
        user_id_embedded = model.ranking_model.userid_embeddings(b['user_id'])
        category_embedded = model.ranking_model.category_embeddings(b['category'])
        prediction = model.ranking_model.ratings(tf.concat([user_id_embedded, category_embedded], axis=1))
        test_mission[b['category'].numpy()[0]] = prediction.numpy()[0][0]

    # Sort the predictions by score and print the titles
    res = {}
    
    count = 0
    for b in sorted(test_mission, key=test_mission.get, reverse=True):
        if count < 3:
            res[count] = b.decode('utf-8')
            count += 1
        else:
            break

    return res