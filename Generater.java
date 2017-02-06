package com.ben.suduku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Generater {
	private int[][] table = new int[9][9];
	
	public int[][] run() {
		//填159区域
		fillArea(0);
		fillArea(4);
		fillArea(8);
		int[] blocks=new int[]{1,2,3,5,6,7};
		//当前填写块
		int currentBlockIndex=0;
		//当前填写数字
		int currentFillNo=1;
		//依次填写数字1,2,3,4,5,6,7,8,9
		while(currentFillNo<10){
			//尝试填cell
			boolean fillResult=tryFillCell(blocks[currentBlockIndex],currentFillNo);
			//填入cell成功
			if(fillResult){
				//填写下一个块
				currentBlockIndex++;
				//已到最后一块,填写下个数字
				if(currentBlockIndex==blocks.length){
					currentBlockIndex=0;
					currentFillNo++;
				}
			}else{//没有可填cell
				//回溯填写上一个块
				currentBlockIndex--;
				//已回溯到第1个块
				if(currentBlockIndex<0){
					//回溯数字
					currentFillNo--;
					if(currentFillNo<0){
						throw new RuntimeException("生成失败");
					}
					//回到最后一个块       
					currentBlockIndex=blocks.length-1;
				}
			}
		}
		
		return table;
	}
	/**
	 * 返回在块中可填写cell的坐标,-1表示无
	 * @param block
	 * @param tryValue
	 * @return
	 */
	private boolean tryFillCell(int block,int tryValue){
		//开始判断位置,如果块中已有,则从已有位置开始下一个
		int cell=0;
		int[][] t=table;
		for(int i=0;i<9;i++){
			int row=block/3*3+i/3;
			int col=block%3*3+i%3;
			if(t[row][col]==tryValue){//块中已有,则表示再次填写
				t[row][col]=0;//重置
				//块中已有,则从已有位置开始下一个
				cell=i+1;
				break;
			}
		}
		
		while(cell<9){
			int row=block/3*3+cell/3;
			int col=block%3*3+cell%3;
			//可以放入
			if(table[row][col]==0){
				if(judgeCellValueCorrect(row, col, tryValue)){
					table[row][col]=tryValue;
					return true;
				}
			}
			cell++;
		}
		//没有可放入cell
		return false;
	}
	/**
	 * 判断在某块的cell中可以填写value
	 * @param blockIndex
	 * @param cell
	 * @param tryValue
	 * @return
	 */
	private boolean judgeCellValueCorrect(int row,int col,int tryValue){
		//判断行
		int[][] t=table;
		int[] cells=t[row];
		for(int i=cells.length;i-->0;){
			if(cells[i]==tryValue){
				return false;
			}
		}
		//判断列
		for(int i=9;i-->0;){
			if(t[i][col]==tryValue){
				return false;
			}
		}
		return true;
	}
	private static void shuffle(int[] arr) {
		int r = 0, t = 0;
		for (int i = arr.length; i-- > 1;) {
			r = (int) (Math.random() * 9);
			t = arr[r];
			arr[r] = arr[8];
			arr[8] = t;
		}
	}

	/**
	 * 填充区域(1-9)
	 * 
	 * @param area
	 */
	public void fillArea(int area) {
		int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		shuffle(nums);
		int[][] t=this.table;
		for(int i=0;i<9;i++){
			t[area/3*3+i/3][area%3*3+i%3]=nums[i];
		}
	}
	private void print(){
		
		for (int i = 0; i < 9;i++ ) {
			for (int j = 0; j < 9; ++j) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		for(int i=0;i<1;i++){
		Generater g = new Generater();
			g.run();
			g.print();
		}
		
	}
}
