package uob.oop;

public class NLP {
    /***
     * Clean the given (_content) text by removing all the characters that are not 'a'-'z', '0'-'9' and white space.
     * @param _content Text that need to be cleaned.
     * @return The cleaned text.
     */
    public static String textCleaning(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.1 - 3 marks
        _content = _content.toLowerCase();
        char[] contentArray = _content.toCharArray();

        for(char letter : contentArray) {
            if(Character.isLetterOrDigit(letter) || Character.isWhitespace(letter)) {
                sbContent.append(letter);
            }
        }


        return sbContent.toString().trim();
    }

    /***
     * Text lemmatization. Delete 'ing', 'ed', 'es' and 's' from the end of the word.
     * @param _content Text that need to be lemmatized.
     * @return Lemmatized text.
     */
    public static String textLemmatization(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.2 - 3 marks
        String[] content = _content.split(" ");
        for(String word : content) {
            if(word.endsWith("ing")){
                sbContent.append(word, 0, word.length()-3).append(" ");
            } else if(word.endsWith("ed")){
                sbContent.append(word, 0, word.length()-2).append(" ");
            } else if(word.endsWith("es")){
                sbContent.append(word, 0, word.length()-2).append(" ");
            } else if(word.endsWith("s")){
                sbContent.append(word, 0, word.length()-1).append(" ");
            } else {
                sbContent.append(word).append(" ");
            }

        }

        return sbContent.toString().trim();
    }

    /***
     * Remove stop-words from the text.
     * @param _content The original text.
     * @param _stopWords An array that contains stop-words.
     * @return Modified text.
     */
    public static String removeStopWords(String _content, String[] _stopWords) {
        StringBuilder sbConent = new StringBuilder();
        //TODO Task 2.3 - 3 marks
        String[] content = _content.split(" ");

        for(String word : content){
            for(String stopWord : _stopWords){
                if(!word.equals(stopWord)){
                    sbConent.append(word).append(" ");
                }

            }
        }
        return sbConent.toString().trim();
    }

}
