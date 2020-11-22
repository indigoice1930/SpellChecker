package HW9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/*
This spell checker only catches the spelling errors for words,
all other type of strings such as numbers will be assumed to be correct.
 */

public class SpellChecker {
    public static String adjusted(String s){

        char lastChar = s.charAt(s.length()-1);
        //determine if it's digit or letter, if not, it's a punctuation, and can be removed
        if(!Character.isDigit(lastChar) && !Character.isLetter(lastChar)){
            s = s.substring(0,s.length()-1);
        }
        //adjust each character to lower case
        char[] arr = s.toCharArray();
        for(int i = 0; i < s.length(); i++){
            if(Character.isUpperCase(arr[i])){
                arr[i] = Character.toLowerCase(arr[i]);
            }
        }
        return new String(arr);
    }

    public static boolean noLetter(String s){
        boolean noLetter = true;
        for(int i = 0; i < s.length(); i++){
            if(Character.isLetter(s.charAt(i))) {
                noLetter = false;
            }
        }
        return noLetter;
    }

    public static void main(String[] args) {

        //get words and put into hashset
        Scanner words = null;
        try{
            words = new Scanner(new FileInputStream("words.txt"));
        }catch(FileNotFoundException e) {
            System.out.println("File words.txt was not found");
            System.out.println("or could not be opened.");
            System.exit(0);
        }

        HashSet<String> correctWords = new HashSet<>();
        while(words.hasNextLine()){
            correctWords.add(words.nextLine());
        }

        //go through the paragraph to see if there exist words that are not from the words file
        Scanner paragraghInput = null;
        try{
            paragraghInput = new Scanner(new FileInputStream("newyorktimespaper.txt"));
        }catch(FileNotFoundException e) {
            System.out.println("File newyorktimespaper.txt was not found");
            System.out.println("or could not be opened.");
            System.exit(0);
        }

        HashSet<String> listOfIncorrectWords = new HashSet<>();
        while(paragraghInput.hasNext()){
            String word = paragraghInput.next();
            //Only considers it as potential errors when it has at least a letter in the string
            if(!noLetter(word) && !correctWords.contains(adjusted(word))){
               listOfIncorrectWords.add(word);
            }
        }

        //print out the potentially incorrect words
        System.out.println("Potentially incorrect words:");
        for(String word : listOfIncorrectWords){
            System.out.println(word);
        }
    }
}
