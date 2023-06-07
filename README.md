# Machine Learning Documentation

## Recommendation System Model
A recommendation system is a type of information filtering system that predicts and suggests items or content that a user may be interested in. Its main purpose is to assist users in discovering relevant and personalized recommendations from a vast pool of choices, such as products, movies, music, articles, or even friends in social networks.

### Collaborative Filtering
This algorithm is a type of collaborative filtering algorithm that takes into account the number of surveys that a user has filled out when making recommendations.

<p align="center"> <img src="https://github.com/zenrif/Cendakala/assets/101646114/9c9187f3-a89a-4473-895f-dd40e94a9d86"></p>
<p align="center">Collaborative Filtering Illustration</p>

### Datasets
#### Dataset dataset_kuesioner
The questionnaire dataset consists of 4 columns, namely number, judul, category_1, and category_2. Number is the index of the dataset, judul is the title of the questionnaire, category_1 and category_2 are the categories of the questionnaire (ie health, education, etc). The data in the dataset was generated with the Python library and completed manually by team members.

Dataset Preview<br>

<table>
        <tr>
            <th>judul</th>
            <th>category_1</th>
            <th>category_2</th>
        </tr>
        <tr>
            <td>Efektivitas Program Pendidikan Kesehatan dalam Meningkatkan Pengetahuan dan Perilaku Hidup Sehat Siswa Sekolah Dasar: Penelitian pada Sekolah Dasar di Kota Makassar</td>
            <td>Kesehatan</td>
            <td>Pendidikan</td>
        </tr>
        <tr>
            <td>Tinjauan atas Implementasi Program Pendidikan Pariwisata di Sekolah: Penilaian Efektivitas dan Dampak pada Pengetahuan dan Minat Siswa terhadap Industri Pariwisata</td>
            <td>Pendidikan</td>
            <td>Pariwisata</td>
        </tr>
        <tr>
            <td>Pengaruh Hukum terhadap Kebebasan Berpendapat dan Ekspresi dalam Era Digital: Studi Kasus tentang Batasan dan Perlindungan Hukum dalam Konteks Teknologi Informasi dan Komunikasi</td>
            <td>Hukum</td>
            <td>Teknologi Informasi dan Komunikasi</td>
        </tr>
        <tr>
            <td>Evaluasi Fasilitas dan Infrastruktur Olahraga di Destinasi Pariwisata</td>
            <td>Pariwisata</td>
            <td>Olahraga dan Rekreasi</td>
        </tr>
        <tr>
            <td>Kontribusi Seni dan Budaya dalam Mendorong Perlindungan Lingkungan dan Konservasi Warisan Budaya</td>
            <td>Lingkungan dan Konservasi</td>
            <td>Seni dan Budaya</td>
        </tr>
        <tr>
            <td>Studi Tentang Inovasi Bisnis dalam Menyelesaikan Tantangan Sosial dan Kemanusiaan</td>
            <td>Bisnis dan Industri</td>
            <td>Sosial dan Kemanusiaan</td>
        </tr>
    </table>

#### Dataset dataset_dummy
The dummy dataset consists of 3 columns, namely user_id, kategori, and history. user_id is the identification of the user in the database, category is the category of the questionnaire (i.e. health, education, etc.), and history describes how many questionnaires were completed by users in the same category. The data in the dummydataset is generated with a Python library.

Dataset Preview<br>

| user_id   | category                           | history  |
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

### Deployment
The model architecture is deployed to the backend service. The model then gets the data, processes it, and sends the recommendation to the application.

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
* [Jupyter Notebook](https://jupyter.org/install).
* Kaggle API Token.
* Latest Tensorflow Version 2.8.2.
* Python Version 3.6 or above.

