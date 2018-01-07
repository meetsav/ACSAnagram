/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 4;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    public ArrayList<String> wordlist=new ArrayList<String>();
    public HashSet wordset=new HashSet();
    public HashMap<String,ArrayList<String>> latterToWord=new HashMap<>();
    public HashMap<Integer,ArrayList<String>> sizeToWord=new HashMap<>();
    public int wordLength=DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {

            String word = line.trim();

            wordlist.add(word);
            wordset.add(word);
            ArrayList<String> count=new ArrayList<>();
            if(sizeToWord.containsKey(word.length()))
            {

                count=sizeToWord.get(word.length());//used to get arraylist whose key is specific word length
                count.add(word);//if that key is already exist then simply add that word to that arraylist
                sizeToWord.put(word.length(),count);//put that entire key and arraylist stucture into hashmap
            }
            else
            {
                count.add(word);//if key not exist then make new arraylis
                sizeToWord.put(word.length(),count);//put that key value pair into hashmap
            }

            String Sorrted=sortLatter(word);//sorting method 
            ArrayList<String> value=new ArrayList<>();
            if(latterToWord.containsKey(Sorrted))
            {
                value=latterToWord.get(Sorrted);//method for get all value of sorrted key
                value.add(word);//add that word
                latterToWord.put(Sorrted,value);//put that key valuepair into hashmap

            }
            else
            {
                value.add(word);//if not exist than create new arraylist
                latterToWord.put(Sorrted,value);//put key value pair into hashmap
            }
        }
    }

    public boolean isGoodWord(String word, String base) {//word is only good word when it exist in dictionary and do not contain base mean given word string
        if(wordset.contains(word) && !word.contains(base))
        {
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        return result;
    }
    public String sortLatter(String word)//sorting method 
    {
        char[] chararray=word.toCharArray();
        Arrays.sort(chararray);
        String sortedword = new String(chararray);
        return sortedword;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {//add one more later to given word 
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> temp=new ArrayList<>();
        for(int i=0;i<26;i++)
        {
            String oneaddword=word+(char)(97+i);
            String newWord=sortLatter(oneaddword);
            if(latterToWord.containsKey(newWord))//if anagram hash map only contain that new word
            {
                temp=latterToWord.get(newWord);//take arraylist to tmparary
                for(String tword:temp)
                {
                    if(!tword.contains(word))//word+1 later word must not contain base word
                    {
                        result.add(tword);
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {//change for best pick word from dictionary
        ArrayList<String> minAnagramWords = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();
        for(String key:sizeToWord.get(wordLength))//take one string at a time after getting specific length 
        {
            if(latterToWord.get(sortLatter(key)).size()>=MIN_NUM_ANAGRAMS)//give arraylist size
            {
                temp=latterToWord.get(sortLatter(key));//give you array list for pertcular key
                for(String k:temp)
                {
                    minAnagramWords.add(k);
                }
            }
        }

        int dictlength=minAnagramWords.size();
        String randomstring=minAnagramWords.get(random.nextInt(dictlength));
        if(wordLength<=MAX_WORD_LENGTH)
            wordLength++;
        return randomstring;
    }
}
