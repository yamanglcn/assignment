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
        int[] idfWordCount = new int[vocabularyList.length];
        //TODO 4.3 - 10 marks

        //Calculate Frequency
        for (int i = 0; i < _cleanedContents.length; i++){

            String[] tmpArray = _cleanedContents[i].split(" ");
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

        //Number of documents where the word z appears.
        for (int i = 0; i < vocabularyList.length; i++) {
            for (int j = 0; j < _cleanedContents.length; j++) {
                if (frequency[j][i] != 0) {
                    idfWordCount[i]++;
                }
            }
        }


        //TF Calculation
        for (int i = 0; i < _cleanedContents.length; i++) {
            String[] tmpArray = _cleanedContents[i].split(" ");
            for (int j = 0; j < vocabularyList.length; j++) {
                myTF[i][j] = (double) frequency[i][j] / tmpArray.length;
            }
        }

        //IDF Calculation
        for (int i = 0; i < _cleanedContents.length; i++) {
            String[] tmpArray = _cleanedContents[i].split(" ");
            for (int j = 0; j < vocabularyList.length; j++) {
                myIDF[i][j] = Math.log((double) _cleanedContents.length /idfWordCount[j]) + 1;
            }
        }

        //TFIDF Calculation
        for (int i = 0; i < _cleanedContents.length; i++) {
            String[] tmpArray = _cleanedContents[i].split(" ");
            for (int j = 0; j < vocabularyList.length; j++) {
                myTFIDF[i][j] = myTF[i][j] * myIDF[i][j];
            }
        }

        return myTFIDF;
    }

    public String[] buildVocabulary(String[] _cleanedContents) {
        String[] arrayVocabulary = null;
        //TODO 4.4 - 10 marks
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

        boolean[] inList = new boolean[length];

        for (int i = 0; i < length; i++) {
            if (!inList[i]) {
                for (int j = i + 1; j < length; j++) {
                    if (wordArray[i].equals(wordArray[j])) {
                        inList[j] = true;
                    }
                }
            }
        }

        int finalLength = 0;
        for (boolean count : inList) {
            if (!count) {
                finalLength++;
            }
        }

        String[] tmpVocab = new String[length];
        arrayVocabulary = new String[finalLength];

        int finalIndex = 0;
        for (int i = 0; i < length; i++) {
            if (!inList[i]) {
                tmpVocab[finalIndex++] = wordArray[i];
            }
        }

        for (int i = 0; i < finalLength; i++) {
            arrayVocabulary[i] = tmpVocab[i];
        }

        return arrayVocabulary;
    }

    public double[][] newsSimilarity(int _newsIndex) {
        int length = this.newsTFIDF.length;
        double[][] mySimilarity = new double[length][2];
        Vector indexTFIDF = new Vector(this.newsTFIDF[_newsIndex]);

        //CS Calculation
        for (int i = 0; i < length; i++) {
        Vector tmpVector = new Vector(this.newsTFIDF[i]);
        mySimilarity[i][0] = i;
        mySimilarity[i][1] = indexTFIDF.cosineSimilarity(tmpVector);
        }

        for (int i = 0; i < mySimilarity.length-1; i++) {
            for (int j = 0; j < mySimilarity.length-i-1; j++) {
                if (mySimilarity[j][1] < mySimilarity[j+1][1]) {
                    double[] tmpValue = mySimilarity[j];
                    mySimilarity[j] = mySimilarity[j+1];
                    mySimilarity[j+1] = tmpValue;
                }
            }
        }
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
