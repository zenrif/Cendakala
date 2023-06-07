# Import Library
import json
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

### FUNCTION ###
def survey_similarity_model(query, choices, top_k=6):
    # Extract survey texts from choices
    survey_texts = [choice['survey'] for choice in choices]

    # Create TF-IDF vectors for query and choices
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(survey_texts)

    # Calculate cosine similarity between query and choices
    cosine_similarities = cosine_similarity(vectorizer.transform([query]), tfidf_matrix).flatten()

    # Get the indices of the top matches
    top_indices = cosine_similarities.argsort()[-top_k:][::-1]

    # Perform fuzzy matching and return the results
    results = []
    for index in top_indices:
        if survey_texts[index] != query:
            survey_id = choices[index]['surveyID']
            results.append(survey_id)

    return results


### END OF FUNCTION ###

