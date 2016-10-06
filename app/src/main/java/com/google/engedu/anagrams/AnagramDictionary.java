package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static java.sql.Types.NULL;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private int wordlength=DEFAULT_WORD_LENGTH;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String>wordlist = new ArrayList<>();
    private HashSet<String>wordSet=new HashSet<String>();
    private HashMap<String,ArrayList<String>>lettersToWord=new HashMap<String, ArrayList<String>>();
    private HashMap<Integer,ArrayList<String>>sizeToWords=new HashMap<Integer, ArrayList<String>>();


    public String sortLetters(String A){
        String B =A;
        char[] chars=B.toCharArray();
        Arrays.sort(chars);
        String sorted= new String(chars);
        return sorted;
    }

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            String  sword =this.sortLetters(word);
                if (lettersToWord.containsKey(sword)) {
                       ArrayList<String> wordlist = lettersToWord.get(sword);
                        wordlist.add(word);
                        lettersToWord.put(sword, wordlist);
                        sizeToWords.put(word.length(),wordlist);

                }
            else{
                    ArrayList<String> wordlist2 = lettersToWord.get(sword);
                        wordlist2.add(word);
                        lettersToWord.put(sword, wordlist2);
                        sizeToWords.put(word.length(),wordlist2);


                }


        }
    }

    public boolean isGoodWord(String word, String base) {
        boolean exists=wordSet.contains(word);
        String aexists;
        if (word.indexOf(base)!=-1) aexists="false";
        else aexists="true";

        if(exists && aexists=="true"){
            return true;
        }
        else
            return false;
    }



    public ArrayList<String> getAnagrams(String targetWord) {
        return lettersToWord.get(sortLetters((targetWord)));
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        String aword=this.sortLetters(word);
        ArrayList<String> result = new ArrayList<String>();
        char add='a';
        for(;(int)add<=122;add++)
        {
            aword=aword+add;
            aword=this.sortLetters(aword);
            if(lettersToWord.containsKey(aword)){
                ArrayList<String> temp=lettersToWord.get(aword);
                for (String am:temp ){
                    Log.v("GAME",am);
                    result.add(am);
                }


            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int length = 0;
        ArrayList<String> t=sizeToWords.get(wordlength);
        int sizeArrayLength=t.size();
        for (; length < MIN_NUM_ANAGRAMS; ) {
            String w = t.get(random.nextInt(wordlist.size()));
            length = getAnagramsWithOneMoreLetter(w).size();
            if (length >= MIN_NUM_ANAGRAMS){
                if(wordlength<MAX_WORD_LENGTH) wordlength++;
                return w;
            }
        }

        return "stop";
    }
}
