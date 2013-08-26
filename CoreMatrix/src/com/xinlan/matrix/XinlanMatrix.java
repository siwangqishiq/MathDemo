package com.xinlan.matrix;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Panyi
 * 
 */
public class XinlanMatrix {
	private int m, n;
	private int[][] data;

	public XinlanMatrix() {
	}

	public XinlanMatrix(int m, int n) {
		this.m = m;
		this.n = n;
		data = new int[m][n];
	}

	public void setData(int[][] data) {
		this.data = data;
	}

	public void toRowSimplest() {
		if (data == null) {
			return;
		}
		for (int startX = 0, startY = 0, rowsNum = data.length, side = data[0].length; startY < rowsNum
				&& startX < side; startY++, startX++) {
			if (startY >= rowsNum - 1) {
				doReduction(data[startY]);
				return;
			}
			int base[] = data[startY];
			for (int i = startY + 1; i < rowsNum; i++) {
				int baseMulti = data[i][startX];
				int dataMulti = base[startX];
				if(dataMulti==0 || baseMulti==0){
					break;
				}
				for (int j = startX, colNum = data[startY].length; j < colNum; j++) {
					base[j] *= baseMulti;
					data[i][j] *= dataMulti;
					data[i][j] -= base[j];
				}// end for j
			}// end for i
			doReduction(base);//Ô¼·Ö¾ØÕóÐÐ
		}// end for
	}

	public void show() {
		DecimalFormat df = new DecimalFormat("%10s");
		for (int i = 0, row = data.length; i < row; i++) {
			for (int j = 0, col = data[i].length; j < col; j++) {
				System.out.print(String.format("%10s", data[i][j]));
			}// end for j
			System.out.println();
		}// end for i
	}

	private static int[] doReduction(int[] a) {
		int startIndex = 0;
		for (int i = 0, acount = a.length; i < acount; i++) {
			if (a[i] != 0) {
				startIndex = i;
				break;
			}
		}
		List<Integer> same = factorAnalysis(a[startIndex]);
		for (int i = 0, acount = a.length; i < acount; i++) {
			if (a[i] != 0)
				same.retainAll(factorAnalysis(a[i]));
		}// end for i

		for (int i = 0; i < same.size(); i++) {
			if (isCanDiv(a, same.get(i))) {
				divArray(a, same.get(i));
			}
		}// end for
		return a;
	}

	private static boolean isCanDiv(int a[], int divNum) {
		for (int i = 0, length = a.length; i < length; i++) {
			if (a[i] % divNum != 0) {
				return false;
			}
		}
		return true;
	}

	private static void divArray(int a[], int divNum) {
		for (int i = 0, length = a.length; i < length; i++) {
			a[i] = a[i] / divNum;
		}
	}

	private static List<Integer> factorAnalysis(int number) {
		List<Integer> ret = new ArrayList<Integer>();
		if (number == 0) {
			return ret;
		}

		if (number < 0) {
			ret.add(-1);
			number = Math.abs(number);
		}

		int start = 1;
		while (number != 1) {
			if (number % start == 0) {
				if (start != 1) {
					ret.add(start);
					number = number / start;
				} else {
					start++;
				}
			} else {
				start++;
			}
		}// end while
		return ret;
	}

	public static void main(String[] agrs) {
		 XinlanMatrix matrix = new XinlanMatrix(10, 10);
		 int[][] a = { {2,1,1,7},{1,-3,1,-2},{1,1,-1,0}};
		 matrix.setData(a);
		 matrix.show();
		 matrix.toRowSimplest();
		 System.out.println();
		 System.out.println();
		 matrix.show();
	}
}// end class
