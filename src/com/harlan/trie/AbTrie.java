package com.harlan.trie;


import java.util.HashMap;
import java.util.Map;

public abstract class AbTrie {

	// �ж��ֵ������Ƿ��и��ַ�����
	public abstract boolean contains(String word);

	// ���ظ��ַ������ֵ����г��ֵĴ�����
	public abstract int frequency(String word);

	// ����һ���ַ�����
	public abstract int insert(String word);

	// ɾ��һ���ַ�����
	public abstract boolean remove(String word);

	// �����ֵ����в�ͬ�ַ����ĸ�����Ҳ���Ǳ���Ĳ�ͬ�ַ����ĸ�����
	public abstract int size();

	/**
	 * TrieNode�࣬����һ���ַ� ���ұ���һ����ǰ�ַ�����ʾ���ַ����ĳ��ִ���
	 * 
	 * @author Harlan
	 *
	 */
	class TrieNode {
		char c;
		int occurances;
		Map<Character, TrieNode> children;

		TrieNode(char c) {
			this.c = c;
			occurances = 0;
			children = null;
		}

		int insert(String s, int pos) {
			// �������մ�����ֱ�ӷ���
			// �˷�������ʱ��pos=0��ʼ�ĵݹ���ã�posָ���ǲ���ĵ�pos���ַ�
			if (s == null || pos >= s.length())
				return 0;

			// �����ǰ�ڵ�û�к��ӽڵ㣬��newһ��
			if (children == null)
				children = new HashMap<Character, TrieNode>();

			// ����һ��TrieNode
			char c = s.charAt(pos);
			TrieNode n = children.get(c);

			// ȷ���ַ������ڼ���Ҫ����Ľڵ���
			if (n == null) {
				n = new TrieNode(c);
				children.put(c, n);
			}

			// ����Ľ���ʱֱ�����һ���ַ����룬���صĽ���Ǹ��ַ������ֵĴ���
			// �������������һ���ַ�
			if (pos == s.length() - 1) {
				n.occurances++;
				return n.occurances;
			} else {
				return n.insert(s, pos + 1);
			}
		}

		boolean remove(String s, int pos) {
			if (children == null || s == null)
				return false;

			// ȡ����pos���ַ����������ڣ��򷵻�false
			char c = s.charAt(pos);
			TrieNode n = children.get(c);
			if (n == null)
				return false;

			// �ݹ�������Ѿ������ַ��������һ���ַ������occurances=0�������Ѿ�ɾ����
			// ��������ݹ鵽���һ���ַ�
			boolean ret;
			if (pos == s.length() - 1) {
				int before = n.occurances;
				n.occurances = 0;
				ret = before > 0;
			} else {
				ret = n.remove(s, pos + 1);
			}

			// ɾ��֮�󣬱���ɾ������Ҫ���ַ�
			// ���籣��ġ�Harlan����ɾ���ˣ���ô���n������Ҷ�ӽڵ㣬��ζ������Ȼ������Ų������ˣ����ǻ�ռ�ſռ�
			// ���Ա���ɾ�������������Harlan��ɾ���ˣ�����Trie���滹�����⡰Harlan1994��,��ô�ò���Ҫɾ���ַ���
			if (n.children == null && n.occurances == 0) {
				children.remove(n.c);
				if (children.size() == 0)
					children = null;
			}

			return ret;
		}

		TrieNode lookup(String s, int pos) {
			if (s == null)
				return null;

			// ����ҵĴ����Ѿ��������ַ��ĳ��ȣ�˵�����Ѿ��ݹ鵽�����ַ���������ˣ������ַ���������
			if (pos >= s.length() || children == null)
				return null;

			// ����պõ����ַ������һ������ֻ��Ҫ�������һ���ַ���Ӧ�Ľ�㣬���ڵ�Ϊ�գ�����������ڸ��ַ���
			else if (pos == s.length() - 1)
				return children.get(s.charAt(pos));

			// ��������ݹ��ѯ��ȥ��ֱ��û�к��ӽڵ���
			else {
				TrieNode n = children.get(s.charAt(pos));
				return n == null ? null : n.lookup(s, pos + 1);
			}
		}
	}
}
