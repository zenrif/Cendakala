const express = require('express');
const app = express();
const { fuzz, process } = require('fuzzball');
const fs = require('fs');
const stringSimilarity = require('string-similarity');

// Inisiasi Sastrawi Bahasa Indonesia
const StemmerFactory = require('node-stemmer').StemmerFactory;
const factory = new StemmerFactory();
const stemmer = factory.createStemmer();

const indonesianWordList = fs.readFileSync('indonesian-wordlist.lst', 'utf-8').split('\n').map(line => line.trim());
const stopWords = new Set(fs.readFileSync('combined_stop_words.txt', 'utf-8').split('\n').map(line => line.trim()));

function removeStopWords(text) {
  const words = text.split(' ');
  const filteredWords = words.filter(word => !stopWords.has(word.toLowerCase()));
  return filteredWords.join(' ');
}

function handleTypos(text) {
  const words = text.split(' ');
  const handledTypos = words.map(word => {
    if (indonesianWordList.includes(word)) {
      return word;
    } else {
      const ratios = process.extract(
        word, indonesianWordList, { scorer: fuzz.ratio }
      );
      const highestRatio = Math.max(...ratios.map(ratio => ratio[1]));
      const highestRatioWord = ratios.find(ratio => ratio[1] === highestRatio)[0];
      return highestRatioWord;
    }
  });
  return handledTypos.join(' ');
}

function preprocessTextQuery(text) {
  let processedText = text.toLowerCase().replace(/[^\w\s]/g, '');
  processedText = handleTypos(processedText);
  processedText = stemmer.stem(processedText);
  processedText = removeStopWords(processedText);
  return processedText;
}

function preprocessData(text) {
  let processedText = text.toLowerCase().replace(/[^\w\s]/g, '');
  processedText = stemmer.stem(processedText);
  processedText = removeStopWords(processedText);
  return processedText;
}

// DATA JUDUL DISINI
const jsonData = require('./title.json');
const title = Object.values(jsonData.judul);
const formId = Object.values(jsonData.form_id);

function search(query) {
  const results = [];

  const queryPreprocessed = preprocessTextQuery(query);
  for (let i = 0; i < title.length; i++) {
    const titlePreprocessed = preprocessData(title[i]);
    if (titlePreprocessed.includes(queryPreprocessed)) {
      const hasil = {
        id: formId[i],
        title: title[i]
      };
      results.push(JSON.stringify(hasil));
    }
  }

  const queryPreprocessedSplit = preprocessTextQuery(query).split(' ');
  for (const word of queryPreprocessedSplit) {
    for (let i = 0; i < title.length; i++) {
      const titlePreprocessed = preprocessData(title[i]);
      if (titlePreprocessed.includes(word)) {
        const ratio = fuzz.ratio(queryPreprocessed, titlePreprocessed);
        if (ratio > 60 && !results.find(result => result.title === title)) {
          const hasil = {
            id: formId[i],
            title: title[i]
          };
          results.push(JSON.stringify(hasil));
        }
      }
    }
  }

  return results;
}

app.get('/search', (req, res) => {
  try {
    const input = req.query.input;

    if (input) {
      const results = search(input);

      const data = {
        input: input,
        hasil: results
      };

      const response = {
        message: 'Success',
        data: data,
        code: 200
      };

      // Return a JSON response
      res.status(200).json(response);
    } else {
      const response = {
        message: 'Input not found',
        code: 400
      };
      res.status(400).json(response);
    }
  } catch (error) {
    const response = {
      message: error.message,
      code: 500
    };
    res.status(500).json(response);
  }
});