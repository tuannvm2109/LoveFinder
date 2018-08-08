package com.example.manhtuan.lovefinder.NLP;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

public class OpenNLPNamedEntityRecognition {
    InputStreamFactory model;

    public OpenNLPNamedEntityRecognition(InputStreamFactory model) {
        this.model = model;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getName(String message){
        ObjectStream sampleStream = null;
        try {
            sampleStream = new NameSampleDataStream(
                    new PlainTextByLineStream(model, StandardCharsets.UTF_8));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, "70");
        params.put(TrainingParameters.CUTOFF_PARAM, "1");

        TokenNameFinderModel nameFinderModel = null;
        try {
            nameFinderModel = NameFinderME.train("en", null, sampleStream,
                    params, TokenNameFinderFactory.create(null, null, Collections.<String, Object>emptyMap(), new BioCodec()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TokenNameFinder nameFinder = new NameFinderME(nameFinderModel);
        String[] words = message.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
//            Log.d("asdf",words[i]);
        }
        Span[] names = nameFinder.find(words);

        for(Span name:names){
            String personName="";
            for(int i=name.getStart();i<name.getEnd();i++){
                personName+=words[i]+" ";
            }
//            Log.d("asdf",name.getType()+" : "+personName+"\t [probability="+name.getProb()+"]");
        }
    }
}
