package uob.oop;

public class HtmlParser {
    /***
     * Extract the title of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the title if it's been found. Otherwise, return "Title not found!".
     */
    public static String getNewsTitle(String _htmlCode) {
        //TODO Task 1.1 - 5 marks
        String beginTitleSemantic = "<title>";
        String endTitleSemantic = "</title>";
        int beginTitleIndex = _htmlCode.indexOf(beginTitleSemantic);
        int endTitleIndex = _htmlCode.indexOf(endTitleSemantic);

        if (beginTitleIndex != -1) {
             _htmlCode = _htmlCode.substring(beginTitleIndex+7, endTitleIndex-7);
             String titleEndSymbol = "|";
             int symbolIndex = _htmlCode.indexOf(titleEndSymbol);
             return _htmlCode.substring(0, symbolIndex-1);
        } else {
            return "Title not found!";
        }
    }

    /***
     * Extract the content of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the content if it's been found. Otherwise, return "Content not found!".
     */
    public static String getNewsContent(String _htmlCode) {
        //TODO Task 1.2 - 5 marks
        String beginContentSemantic = "articleBody";
        int beginIndex = _htmlCode.indexOf(beginContentSemantic);

        if (beginIndex != 1) {
            _htmlCode = _htmlCode.substring(beginIndex+12);
            String endContentSemantic = "\",";
            int endIndex = _htmlCode.indexOf(endContentSemantic);
            _htmlCode = _htmlCode.substring(3, endIndex-1);
            return _htmlCode.toLowerCase();
        } else {
            return "Content not found!";
    }
    }


}
