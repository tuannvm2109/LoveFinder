package com.example.manhtuan.lovefinder.NLP;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class OpenNLPSentimentAnalysis {
    DoccatModel model;
    InputStream trainData;

    public OpenNLPSentimentAnalysis(InputStream trainData) {
        this.trainData = trainData;
    }

    public void trainModel() {
        try {
            ObjectStream lineStream = new PlainTextByLineStream(trainData, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
            model = DocumentCategorizerME.train("vi", sampleStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (trainData != null) {
                try {
                    trainData.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getLikePoint(String message) {
//        String[] words = message.split(",");
//        for (int i = 0; i < words.length; i++) {
//            words[i] = words[i].replaceAll("[^\\w]", "");
//        }
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        double[] outcomes = myCategorizer.categorize(message);
//        Log.d("asdSentimentAnalysis","Phan tich : " + message);
        for (int i = 0; i < outcomes.length; i++) {
//            Log.d("asdSentimentAnalysis",outcomes[i] +"");
        }

        String category = myCategorizer.getBestCategory(outcomes);
        if (category.equalsIgnoreCase("1")) {
//            Log.d("asdSentimentAnalysis","score: 1");
            return 1;
        } else {
//            Log.d("asdSentimentAnalysis","score: -1");
            return -1;
        }
    }
}
