package uob.oop;

import java.text.DecimalFormat;

public class NewsClassifier {
    public String[] myHTMLs;
    public String[] myStopWords = new String[127];
    public String[] newsTitles;
    public String[] newsContents;
    public String[] newsCleanedContent;
    public double[][] newsTFIDF;

    private final String TITLE_GROUP1 = "Osiris-Rex's sample from asteroid Bennu will reveal secrets of our solar system";
    private final String TITLE_GROUP2 = "Bitcoin slides to five-month low amid wider sell-off";

    public Toolkit myTK;

    public NewsClassifier() {
        myTK = new Toolkit();
        myHTMLs = myTK.loadHTML();
        myStopWords = myTK.loadStopWords();

        loadData();
    }

    public static void main(String[] args) {
        NewsClassifier myNewsClassifier = new NewsClassifier();

        myNewsClassifier.newsCleanedContent = myNewsClassifier.preProcessing();

        myNewsClassifier.newsTFIDF = myNewsClassifier.calculateTFIDF(myNewsClassifier.newsCleanedContent);

        //Change the _index value to calculate similar based on a different news article.
        double[][] doubSimilarity = myNewsClassifier.newsSimilarity(0);

        System.out.println(myNewsClassifier.resultString(doubSimilarity, 10));

        String strGroupingResults = myNewsClassifier.groupingResults(myNewsClassifier.TITLE_GROUP1, myNewsClassifier.TITLE_GROUP2);
        System.out.println(strGroupingResults);
    }

    public void loadData() {
        //TODO 4.1 - 2 marks
        newsTitles = new String[myHTMLs.length];
        newsContents = new String[myHTMLs.length];
        for (int i = 0; i < myHTMLs.length; i++) {
            newsTitles[i] = HtmlParser.getNewsTitle(myHTMLs[i]);
            newsContents[i] = HtmlParser.getNewsContent(myHTMLs[i]);
        }
    }

    public String[] preProcessing() {
        String[] myCleanedContent = null;
        //TODO 4.2 - 5 marks
        myCleanedContent = new String[myHTMLs.length];

        for (int i = 0; i < myHTMLs.length; i++) {
            myCleanedContent[i] = NLP.textCleaning(newsContents[i]);
            myCleanedContent[i] = NLP.textLemmatization(newsContents[i]);
            myCleanedContent[i] = NLP.removeStopWords(newsContents[i], myStopWords);
        }

        return myCleanedContent;
    }

    public double[][] calculateTFIDF(String[] _cleanedContents) {
        String[] vocabularyList = buildVocabulary(_cleanedContents);
        double[][] myTFIDF = new double[_cleanedContents.length][vocabularyList.length];
        double[][] myTF = new double[_cleanedContents.length][vocabularyList.length];
        double[][] myIDF = new double[_cleanedContents.length][vocabularyList.length];
        int[][] frequency = new int[_cleanedContents.length][vocabularyList.length];
        //TODO 4.3 - 10 marks
        //Calculate Term Frequency
        for (int i = 0; i < _cleanedContents.length; i++){

            String[] tmpArray = _cleanedContents[i].split(" ");
            //double[] TF = new double[vocabularyList.length];
            int tmpCount = 0;
            for (int j = 0; j < vocabularyList.length; j++) {

                for (int k = 0; k < tmpArray.length; k++) {

                    if (vocabularyList[j].equals(tmpArray[k])) {
                        tmpCount++;
                    }
                }
                frequency[i][j] = tmpCount;
                tmpCount = 0;
            }
        }

        for (int i = 0; i < _cleanedContents.length; i++) {
            String[] tmpArray = _cleanedContents[i].split(" ");
            for (int j = 0; j < vocabularyList.length; j++) {
                myTF[i][j] = (double) frequency[i][j] / tmpArray.length;
            }
        }




        return myTFIDF;
    }

    public String[] buildVocabulary(String[] _cleanedContents) {
        String[] arrayVocabulary = null;
        //TODO 4.4 - 10 marks
        //Gathering all the words in to one array (lines 80-95)
        int length = 0;

        for (int i = 0; i < _cleanedContents.length; i++) {
        String[] tmpArray = _cleanedContents[i].split(" ");
        length = length + tmpArray.length;
        }

        String[] wordArray = new String[length];
        int tmpLength = 0;
        for (int i = 0; i < _cleanedContents.length; i++) {
            String[] tmpArray = _cleanedContents[i].split(" ");
            for (int j = 0; j < tmpArray.length; j++) {
                wordArray[tmpLength + j] = tmpArray[j];
            }
            tmpLength = tmpLength + tmpArray.length;
        }

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (wordArray[i] != null && wordArray[i].equals(wordArray[j])) {

                    for (int k = j; k < length - 1; k++) {
                        wordArray[k] = wordArray[k + 1];
                    }
                    length--;
                    j--;
                }
            }
        }

        arrayVocabulary = new String[length];

        for (int i = 0; i < length; i++) {
            arrayVocabulary[i] = wordArray[i];
        }

        return arrayVocabulary;
    }

    public double[][] newsSimilarity(int _newsIndex) {
        double[][] mySimilarity = null;

        //TODO 4.5 - 15 marks


        return mySimilarity;
    }

    public String groupingResults(String _firstTitle, String _secondTitle) {
        int[] arrayGroup1 = null, arrayGroup2 = null;

        //TODO 4.6 - 15 marks


        return resultString(arrayGroup1, arrayGroup2);
    }

    public String resultString(double[][] _similarityArray, int _groupNumber) {
        StringBuilder mySB = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        for (int j = 0; j < _groupNumber; j++) {
            for (int k = 0; k < _similarityArray[j].length; k++) {
                if (k == 0) {
                    mySB.append((int) _similarityArray[j][k]).append(" ");
                } else {
                    String formattedCS = decimalFormat.format(_similarityArray[j][k]);
                    mySB.append(formattedCS).append(" ");
                }
            }
            mySB.append(newsTitles[(int) _similarityArray[j][0]]).append("\r\n");
        }
        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

    public String resultString(int[] _firstGroup, int[] _secondGroup) {
        StringBuilder mySB = new StringBuilder();
        mySB.append("There are ").append(_firstGroup.length).append(" news in Group 1, and ").append(_secondGroup.length).append(" in Group 2.\r\n").append("=====Group 1=====\r\n");

        for (int i : _firstGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }
        mySB.append("=====Group 2=====\r\n");
        for (int i : _secondGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }

        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

}
