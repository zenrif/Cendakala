# Cendakala, Aplikasi Jual Beli Survey

## Recommendation System Model
A recommendation system is a type of information filtering system that predicts and suggests items or content that a user may be interested in. Its main purpose is to assist users in discovering relevant and personalized recommendations from a vast pool of choices, such as products, movies, music, articles, or even friends in social networks.

## Machine Learning Documentation
### Components
* NLP
* Keras
* Pandas
* NumPy
* Tensorflow
* Sklearn
* Datetime
* Embeddings
* Model Evaluation
* Top-N recommendation
* Accuracy and Loss Graph

### Requirements
* [Google Colaboratory](https://colab.research.google.com/) or [Jupyter Notebook](https://jupyter.org/install).
* Kaggle API Token.
* Latest Tensorflow Version 2.8.2.
* Python Version 3.6 or above.

### Dataset
* [User Dataset](https://drive.google.com/file/d/1onu9HDVevZgUMay2sOUKqJir45plt9cW/view?usp=sharing)

Dataset Preview
| user_id   | kategori                           | history  |
| --------- | ---------------------------------- | -------- |
| 1         | Kesehatan                          | 7        |
| 1         | Pendidikan                         | 4        |
| 1         | Hukum                              | 8        |
| 1         | Keuangan                           | 5        |
| 1         | Pariwisata                         | 7        |
| 1         | Sosial dan Kemanusiaan             | 10       |
| 1         | Lingkungan dan Konservasi          | 3        |
| 1         | Teknologi Informasi dan Komunikasi | 7        |
| 1         | Olahraga dan Rekreasi              | 8        |
| 1         | Seni dan Budaya                    | 5        |
| 1         | Agama dan Kepercayaan              | 4        |
| 1         | Bisnis dan Industri                | 8        |
| 1         | Politik dan Pemerintahan           | 8        |
| 1         | Transportasi dan Logistik          | 3        |
| 1         | Pertanian dan Perikanan            | 6        |
| 2         | Kesehatan                          | 5        |
| 2         | Pendidikan                         | 2        |
| 2         | Hukum                              | 8        |
| 2         | Keuangan                           | 6        |



Data will be retrieved directly from the database. The data is then cleaned and prepared for training. The machine learning model will then be trained on the prepared data. Once the model has been trained, it can be used to recommend surveys to users.

## Models
1. Retrieval models, are used to generate a list of candidate items that are likely to be of interest to a user. This is typically done by finding items that are similar to items that the user has previously interacted with.

2. Fuzzy-Wuzzy, the fuzz string matching ratio is a measure of how similar two strings are. It is calculated by dividing the number of characters that are the same in both strings by the total number of characters in both strings. The higher the ratio, the more similar the two strings are.

### Algorithms
1. Collaborative Filtering : This algorithm is a type of collaborative filtering algorithm that takes into account the number of surveys that a user has filled out when making recommendations.

<p align="center"> <img src="https://github.com/zenrif/Cendakala/blob/507d9931a677e4b8b691921f52e796fac0419f9e/collaborative_filtering_illustration.png"></p>
<p align="center">Collaborative Filtering Illustration</p>

2. Levenshtein distance: This algorithm calculates the number of changes that need to be made to one string to make it match another string. The lower the Levenshtein distance, the higher the fuzz string matching ratio.

<p align="center"> <img src="https://github.com/zenrif/Cendakala/blob/5a260adcdf15fb594cb159f561d556f19a843ca5/fuzzywuzzy_illustration.png"></p>
<p align="center">Fuzzy-Wuzzy Illustration</p>

### Results
(-)

### Deployment
The model architecture is deployed to the backend service. The model then gets the data, processes it, and sends the recommendation to the application.
