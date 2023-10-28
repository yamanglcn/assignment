package uob.oop;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 3.1 - 0.5 marks

    }

    public double getElementatIndex(int _index) {
        //TODO Task 3.2 - 2 marks

        return 0.0; //you need to modify the return value
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 3.3 - 2 marks

    }

    public double[] getAllElements() {
        //TODO Task 3.4 - 0.5 marks

        return null; //you need to modify the return value
    }

    public int getVectorSize() {
        //TODO Task 3.5 - 0.5 marks
        return 0; //you need to modify the return value
    }

    public Vector reSize(int _size) {
        //TODO Task 3.6 - 6 marks

        return null; //you need to modify the return value
    }

    public Vector add(Vector _v) {
        //TODO Task 3.7 - 2 marks

        return null; //you need to modify the return value
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 3.8 - 2 marks

        return null; //you need to modify the return value
    }

    public double dotProduct(Vector _v) {
        //TODO Task 3.9 - 2 marks

        return 0.0; //you need to modify the return value
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 3.10 - 6.5 marks


        return 0.0; //you need to modify the return value
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
