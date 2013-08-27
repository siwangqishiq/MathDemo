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

	/**
	 * 交换矩阵行
	 * 
	 * @param src
	 * @param dst
	 */
	private void swapRows(int src, int dst) {
		int row_num = data.length;
		if (src < 0 || src >= row_num || dst < 0 || dst >= row_num)
			return;
		for (int i = 0, col = data[0].length; i < col; i++) {
			int temp;
			temp = data[src][i];
			data[src][i] = data[dst][i];
			data[dst][i] = temp;
		}// end fori
	}

	private boolean isColAllZero(int index, int from) {
		int colNum = data[0].length;
		int rowNum = data.length;

		if (index < 0 || index >= colNum)
			return false;
		if (from < 0 || from >= rowNum)
			return false;

		for (int i = from; i < rowNum; i++) {
			if (data[i][index] != 0)
				return false;
		}// end for
		
		return true;
	}

	public static void main(String[] agrs) {
		XinlanMatrix matrix = new XinlanMatrix(10, 10);
//		int[][] a = { { 1, -1, 3, -4, 3 }, { 3, -3, 5, -4, 1 },
//				{ 2, -2, 3, -2, 0 }, { 3, -3, 4, -2, -1 } };
//		
		int[][] a = { { 2, -1, -1, 1, 2 }, { 1, 1, -2, 1, 4 },
				{ 4, -6, 2, -2, 4 }, { 3, 6, -9, 7, 9 } };
		matrix.setData(a);
		matrix.show();
		matrix.toRowSimplest();
		System.out.println();
		System.out.println();
		matrix.show();
		// int[] s={0,-14,0,0};
		// doReduction(s);
		// for (int i = 0, length = s.length; i < length; i++) {
		// System.out.print(s[i]+"   ");
		// }
	}

	/**
	 * 转化为行阶梯矩阵
	 */
	public void toRowSimplest() {
		if (data == null) {
			return;
		}

		if (data[0][0] == 0) {
			int first_none_zero = -1;
			for (int i = 1; i < data.length; i++) {
				if (data[i][0] != 0) {
					first_none_zero = i;
				}
			}// end for i
			if (first_none_zero == -1) {
				return;
			}
			swapRows(0, first_none_zero);
		}

		for (int startX = 0, startY = 0, rowsNum = data.length, side = data[0].length; startY < rowsNum
				&& startX < side; startY++, startX++) {
			if (startY >= rowsNum - 1) {
				doReduction(data[startY]);
				return;
			}
			if(isColAllZero(startX,startY)){
				startX++;
			}
			int base[] = data[startY];
			for (int i = startY + 1; i < rowsNum; i++) {
				int baseMulti = data[i][startX];
				int dataMulti = base[startX];
				for (int j = startX, colNum = data[startY].length; j < colNum; j++) {
					if (baseMulti == 0) {
						continue;
					}
					base[j] *= baseMulti;
					data[i][j] *= dataMulti;
					data[i][j] -= base[j];
				}// end for j
				System.out.println("-->" + startX);
				show();
			}// end for i
			doReduction(base);// 约分矩阵行

		}// end for
	}

}// end class
