package com.harlan.trie;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * ����ʵ������һƪ�����У�û�������ַ���ֻ����ĸ�� ��ͬ���ʵĸ������Լ����������������г��ֵĴ���
 * 
 * @author Harlan
 *
 */
public class Sample {

	private Trie mTrie;

	public static void main(String[] args) {
		Sample sample = new Sample();
		sample.mTrie = new Trie(false);
		sample.init();
		System.out.println("���Trie����" + sample.mTrie.size() + "����ͬ�ĵ��ʣ�");
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String s = scanner.nextLine();
			System.out.println("���ִ���Ϊ��" + sample.getCount(s));
		}
	}

	private void init() {
		try {
			InputStream in = new FileInputStream(new File(
					"E:\\Eclipse\\trie\\src\\com\\harlan\\trie\\bible.txt"));
			addToDictionary(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addToDictionary(InputStream f) throws IOException,
			FileNotFoundException {
		long t = System.currentTimeMillis();
		final int bufSize = 1000;
		int read;
		int numWords = 0;
		InputStreamReader fr = null;
		try {
			fr = new InputStreamReader(f);
			char[] buf = new char[bufSize];
			while ((read = fr.read(buf)) != -1) {
				// TODO modify this split regex to actually be useful
				String[] words = new String(buf, 0, read).split("\\W");
				for (String s : words) {
					mTrie.insert(s);
					numWords++;
				}
			}
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
				}
			}
		}
		System.out.println("Read from file and inserted " + numWords
				+ " words into trie in " + (System.currentTimeMillis() - t)
				/ 1000.0 + " seconds.");
	}

	public int getSize() {
		if (mTrie != null) {
			return mTrie.size();
		}
		return 0;
	}

	public int getCount(String s) {
		return mTrie.frequency(s);
	}
}
