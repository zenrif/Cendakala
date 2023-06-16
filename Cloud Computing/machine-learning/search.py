from flask import Flask, request, jsonify
import json
import string
import os
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
from thefuzz import fuzz, process

# inisiasi sastrawi Bahasa Indonesia
factory = StemmerFactory()
stemmer = factory.create_stemmer()

current_dir = os.path.dirname(os.path.abspath(__file__))
word_path = os.path.join(current_dir, 'indonesian-wordlist.lst')
word2_path = os.path.join(current_dir, 'combined_stop_words.txt')

with open(word_path, 'r') as file:
    lines = file.readlines()
    indonesian_word_list = [line.strip() for line in lines]

with open(word2_path) as file:
    stop_words = set(file.read().split())


def remove_stop_words(text):
    words = text.split()
    filtered_words = []
    for word in words:
        if word.lower() not in stop_words:
            filtered_words.append(word)
    return ' '.join(filtered_words)


def handle_typos(text):
    words = text.split()
    handled_typos = []
    for word in words:
        if word in indonesian_word_list:
            handled_typos.append(word)
        else:
            ratios = process.extract(
                word, indonesian_word_list, scorer=fuzz.ratio)
            highest_ratio = max(ratios, key=lambda x: x[1])
            highest_ratio_word = highest_ratio[0]
            handled_typos.append(highest_ratio_word)

    return ' '.join(handled_typos)


def preprocess_text_query(text):

    text = str(text)
    text = text.lower()
    text = text.translate(str.maketrans(
        dict.fromkeys(string.punctuation, ' ')))

    text = handle_typos(text)
    text = stemmer.stem(text)

    text = remove_stop_words(text)

    return text


def preprocess_data(text):

    text = str(text)
    text = text.lower()
    text = text.translate(str.maketrans(
        dict.fromkeys(string.punctuation, ' ')))

    text = stemmer.stem(text)
    text = remove_stop_words(text)

    return text

def search(query, data):
    json_data = data
    title = list(json_data["title"].values())
    form_id = list(json_data["surveyID"].values())
    results = []

    query_preprocessed = preprocess_text_query(query)
    for i in range(len(title)):
        title_preprocessed = preprocess_data(title[i])
        if query_preprocessed in title_preprocessed:
            hasil = {
                "surveyID":  form_id[i],
                "title": title[i]
            }
            results.append(json.dumps(hasil))
        i += 1

    query_preprocessed = preprocess_text_query(query).split()
    for word in query_preprocessed:
        for i in range(len(title)):
            title_preprocessed = preprocess_data(title[i])
            if word in title_preprocessed:
                if title not in results:
                    hasil = {
                        "surveyID":  form_id[i],
                        "title": title[i]
                    }
                    results.append(json.dumps(hasil))
        i += 1

    parsed_data = [json.loads(item) for item in results]
    return parsed_data