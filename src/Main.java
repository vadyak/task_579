import java.io.*;
import java.util.ArrayList;

public class Main {
    private int lineLengthInOriginFile;
    private int[] arr;
    private int finalSequenceLength;
    private ArrayList<Integer> finalSequence = new ArrayList<>();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run(){
        parsingFile();
        searchSequence();
        writeFile();
    }

    private void parsingFile(){
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))){
            int length = Integer.parseInt(br.readLine());
            String stringNum = br.readLine();
            String[] stringArr = stringNum.split(" ");
            int[] intArr = new int[length];
            for (int i = 0; i < length; i++) {
                intArr[i] = Integer.parseInt(stringArr[i]);
            }

            lineLengthInOriginFile = length;
            arr = intArr;
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("file or lines not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchSequence(){
        int sumPositiveSequence = 0;
        int sumNegativeSequence = 0;
        int colZero = 0;
        ArrayList<Integer> positionPositiveSequence = new ArrayList<>();
        ArrayList<Integer> positionNegativeSequence = new ArrayList<>();

        for (int i = 0; i < lineLengthInOriginFile; i++) {
            if (arr[i] > 0) {
                sumPositiveSequence += arr[i];
                positionPositiveSequence.add(i + 1);
            } else if (arr[i] < 0){
                sumNegativeSequence += arr[i];
                positionNegativeSequence.add(i + 1);
            } else colZero++;
        }

        if (colZero == lineLengthInOriginFile) {
            for (int i = 0; i < lineLengthInOriginFile; i++) {
                finalSequence.add(i + 1);
            }
        } else if (sumPositiveSequence > -sumNegativeSequence){
            finalSequence = positionPositiveSequence;
        } else if (sumPositiveSequence < -sumNegativeSequence){
            finalSequence = positionNegativeSequence;
        } else {
            finalSequence = positionPositiveSequence;
        }

        finalSequenceLength = finalSequence.size();
    }

    private String prepareForWriteArr(ArrayList<Integer> arrayList){
        String stringTMP = arrayList.toString();
        String stringArr = stringTMP.substring(1, stringTMP.length() - 1).replaceAll(",", "");
        return stringArr;
    }

    private void writeFile(){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"))){
            bufferedWriter.write(Integer.toString(finalSequenceLength) + "\r\n");
            bufferedWriter.write(prepareForWriteArr(finalSequence));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}