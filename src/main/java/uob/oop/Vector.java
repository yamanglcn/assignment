package uob.oop;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 3.1 - 0.5 marks
    this.doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 3.2 - 2 marks
        double element;
        if (_index >= 0 && _index < doubElements.length) {
            element = doubElements[_index];
        } else {
            element = -1;
        }
        return element; //you need to modify the return value
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 3.3 - 2 marks
        if(_index >= 0 && _index < doubElements.length) {
            doubElements[_index] = _value;
        } else {
            doubElements[doubElements.length-1] = _value;
        }
    }

    public double[] getAllElements() {
        //TODO Task 3.4 - 0.5 marks

        return doubElements; //you need to modify the return value
    }

    public int getVectorSize() {
        //TODO Task 3.5 - 0.5 marks
        return doubElements.length; //you need to modify the return value
    }

    public Vector reSize(int _size) {
        //TODO Task 3.6 - 6 marks
        int initSize = doubElements.length;
        if(_size == doubElements.length || _size <= 0){
            return this;
        } else if(_size < doubElements.length) {
            double[] resizedVector = new double[_size];

            for(int i = 0; i < _size; i++) {
                resizedVector[i] = doubElements[i];
            }
            return new Vector(resizedVector);
        } else if(_size > doubElements.length) {
            double[] resizedVector = new double[_size];

            for(int i = 0; i < doubElements.length; i++) {
                resizedVector[i] = doubElements[i];
            }
            for(int i = doubElements.length; i < _size; i++) {
                resizedVector[i] = -1;
            }

            return new Vector(resizedVector);
        }
        return null; //you need to modify the return value
    }

    public Vector add(Vector _v) {
        //TODO Task 3.7 - 2 marks
        int vectorSize = Math.max(doubElements.length, _v.doubElements.length);
        _v = _v.reSize(vectorSize);

        double[] tmpArray = new double[vectorSize];
        Vector tmpVector;
        tmpVector= this.reSize(vectorSize);

        Vector finalVector = new Vector(tmpArray);

        for(int i = 0; i < vectorSize; i++) {
            finalVector.doubElements[i] = _v.doubElements[i] + tmpVector.doubElements[i];
        }
        return finalVector; //you need to modify the return value
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 3.8 - 2 marks
        int vectorSize = Math.max(doubElements.length, _v.doubElements.length);
        _v = _v.reSize(vectorSize);

        double[] tmpArray = new double[vectorSize];
        Vector tmpVector;
        tmpVector= this.reSize(vectorSize);

        Vector finalVector = new Vector(tmpArray);

        for(int i = 0; i < vectorSize; i++) {
            finalVector.doubElements[i] = tmpVector.doubElements[i] - _v.doubElements[i];
        }
        return finalVector; //you need to modify the return value
    }

    public double dotProduct(Vector _v) {
        //TODO Task 3.9 - 2 marks
        int vectorSize = Math.max(doubElements.length, _v.doubElements.length);
        _v = _v.reSize(vectorSize);

        double[] tmpArray = new double[vectorSize];
        Vector tmpVector;
        tmpVector= this.reSize(vectorSize);

        Vector finalVector = new Vector(tmpArray);

        for(int i = 0; i < vectorSize; i++) {
            finalVector.doubElements[i] = tmpVector.doubElements[i] * _v.doubElements[i];
        }
        double finalProduct = 0;
        for(int i = 0; i < vectorSize; i++){
            finalProduct = finalProduct + finalVector.doubElements[i];
        }

        return finalProduct; //you need to modify the return value
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 3.10 - 6.5 marks
        int vectorSize = Math.max(doubElements.length, _v.doubElements.length);
        _v = _v.reSize(vectorSize);

        double[] tmpArray = new double[vectorSize];
        Vector tmpVector;
        tmpVector= this.reSize(vectorSize);

        double dotProduct = this.dotProduct(_v);

        double magnitudeOne = 0;
        double magnitudeTwo = 0;

        for(int i = 0; i < vectorSize; i++) {
            magnitudeOne = magnitudeOne + (tmpVector.doubElements[i] * tmpVector.doubElements[i]);
            magnitudeTwo = magnitudeTwo + (_v.doubElements[i] * _v.doubElements[i]);
        }

        magnitudeOne = Math.sqrt(magnitudeOne);
        magnitudeTwo = Math.sqrt(magnitudeTwo);

        double finalSimilarity = dotProduct / (magnitudeOne * magnitudeTwo);
        return finalSimilarity; //you need to modify the return value
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;

        if (this.getVectorSize() != v.getVectorSize())
            return false;

        for (int i = 0; i < this.getVectorSize(); i++) {
            if (this.getElementatIndex(i) != v.getElementatIndex(i)) {
                boolEquals = false;
                break;
            }
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
