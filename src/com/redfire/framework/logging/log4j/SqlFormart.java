package com.redfire.framework.logging.log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class SqlFormart {
	public static void main(String[] args) {
		System.out.println(format("\t","SELECT CONF_ID,SYS_CODE,CONF_KEY,CONF_VALUE,DESCRIPTION,CONF_TYPE,STATE,AREA,SYS_NAME,CRT_TIME,CRT_USER,UPT_TIME,UPT_USER FROM redfire_sys_config WHERE CONF_KEY = ?  ".toLowerCase()));
	}
	public static String format(String table, String sql){
		if (sql == null)  return null;
		String text = new SQLFormatter().setText(sql).format().getText();
		String result = "";
		System.out.println("1111--------------"+text);
		/**StringTokenizer st = new StringTokenizer(text, "\n");
		while (st.hasMoreTokens()) {
			String row = st.nextToken();
			row = table + row;
			result += row;
		}*/
		return text; 
	}
}

class SQLFormatter {
	private static final String MAJOR_WORDS = "|SELECT|FROM|WHERE|ORDER BY|GROUP BY|HAVING|UPDATE|SET|INSERT|INTO|VALUES|DELETE|UNION|ALL|MINUS|";

	private static final String MINOR_WORDS = "|COUNT|SUM|AVG|MIN|MAX|DISTINCT|AS|ANY|AND|OR|XOR|NOT|LIKE|IN|EXISTS|IS|NULL|";

	private static final String FUNCTION_WORDS = "|COUNT|SUM|AVG|MIN|MAX|DECODE|";

	private static final String SUB_SELECT = "|SELECT|";

	private static final String ESCAPE_TOKEN = "\001";

	private static final String DELIMITERS = "(),";

	private static final int MAX_INDENTS = 16;

	private static final int NOTHING = 0;

	private static final int SPACE = 1;

	private static final int NEW_LINE = 2;

	private String sEscapes[][] = { { "'", "'", "" }, { "\"", "\"", "" },{ "/*", "*/", "1" }, { "--", "\r\n", "2" } };

	private String sText;

	private String sNewLine;

	private String sIndent;

	private String sDebugString;

	private boolean bCapitalizeMajor;

	private boolean bCapitalizeMinor;

	private boolean bCapitalizeNames;

	private boolean bNewLineBeforeAnd;

	private boolean bDebug;
	private static boolean isName(String s) {
		return !isIn(s,MAJOR_WORDS)&& !isIn(s,MINOR_WORDS);
	}

	private static boolean isFunction(String s) {
		return isIn(s, FUNCTION_WORDS);
	}

	public SQLFormatter format() {
		String sql = sText;
		List<String> vector = new ArrayList<String>();
		for (int i = 0; i < sql.length(); i++) {
			for(int j = 0; j <sEscapes.length; j++) {
				String s1 = sEscapes[j][0];
				String s3 = sEscapes[j][1];
				String s5 = sEscapes[j][2];
				//regionMatches() 用于比较一个字符串中特定区域与另一特定区域，它有一个重载的形式允许在比较中忽略大小写。
				//boolean regionMatches(int startIndex,String str2,int str2StartIndex,int numChars)
				//boolean regionMatches(boolean ignoreCase,int startIndex,String str2,int str2StartIndex,int numChars)
				if (!sql.regionMatches(i, s1, 0, s1.length())){
					continue;
				}
				
				int j1 = i + s1.length();
				int k1 = sql.indexOf(s3, j1);
				if (k1 == -1) {
					k1 = sql.indexOf("\n", j1);
					if (k1 == -1) {
						k1 = sql.indexOf("\r", j1);
						if (k1 == -1)
							k1 = sql.length() - 1;
					}
					k1++;
				} else {
					k1 += s3.length();
				}
				String s6 = sql.substring(i, k1);
				if (s5.equals(" 2 "))
					s6 = "/*" + s6.trim().substring(2) + "*/";
				vector.add(s6);
				String s7 = sql.substring(0, i);
				String s8;
				if (k1 < sql.length()){
					s8 = sql.substring(k1);
				}
				else{
					s8 = "";
				}
				String s9 = "\001";
				if (!s5.equals("")) {
					if (!s7.endsWith("   "))
						s9 = "   " + s9;
					if (!s8.startsWith("   "))
						s9 = s9 + "   ";
				}
				sql = s7 + s9 + s8;
				break;
			}

		}

		Vector vector1 = new Vector();
		for (StringTokenizer stringtokenizer = new StringTokenizer(sql); stringtokenizer
				.hasMoreTokens();) {
			String s2 = stringtokenizer.nextToken();
			for (StringTokenizer stringtokenizer1 = new StringTokenizer(s2,
					" (),", true); stringtokenizer1.hasMoreTokens(); vector1
					.addElement(stringtokenizer1.nextToken()))
				;
		}

		for (int k = 0; k < vector1.size() - 1; k++) {
			String s4 = (String) vector1.elementAt(k) + "   "
					+ (String) vector1.elementAt(k + 1);
			if (isMajor(s4)) {
				vector1.setElementAt(s4, k);
				vector1.removeElementAt(k + 1);
			}
		}

		int l = vector1.size();
		String as[] = new String[l += 2];
		as[0] = "";
		as[l - 1] = "";
		for (int i1 = 0; i1 < vector1.size(); i1++)
			as[i1 + 1] = (String) vector1.elementAt(i1);

		int ai[] = new int[l];
		int ai1[] = new int[l];
		for (int l1 = 0; l1 < l; l1++) {
			boolean flag = false;
			if (isMajor(as[l1]))
				flag = bCapitalizeMajor;
			if (isMinor(as[l1]))
				flag = bCapitalizeMinor;
			if (isName(as[l1]))
				flag = bCapitalizeNames;
			if (flag)
				as[l1] = as[l1].toUpperCase();
		}

		for (int i2 = 1; i2 < l - 1; i2++) {
			ai[i2] = 1;
			if (isMajor(as[i2])) {
				ai[i2 - 1] = 2;
				ai[i2] = 2;
			} else if (as[i2].equals(",")) {
				ai[i2] = 2;
				ai[i2 - 1] = 0;
			} else if (as[i2].equals("(")) {
				ai[i2] = 0;
				if (isFunction(as[i2 - 1]) || isName(as[i2 - 1]))
					ai[i2 - 1] = 0;
			} else if (as[i2].equals(")"))
				    ai[i2 - 1] = 0;
			else if (as[i2].equalsIgnoreCase(" AND "))
				if (bNewLineBeforeAnd)
					ai[i2 - 1] = 2;
				else
					ai[i2] = 2;
		}

		ai[l - 2] = 2;
		int j2 = 0;
		int ai2[] = new int[16];
		for (int k2 = 0; k2 < l; k2++) {
			if (as[k2].equals(")"))
				if (ai2[j2] == 0) {
					j2--;
					if (k2 > 0)
						ai[k2 - 1] = 2;
				} else {
					ai2[j2]--;
				}
			if (isMajor(as[k2]))
				ai1[k2] = j2 * 2;
			else
				ai1[k2] = j2 * 2 + 1;
			if (as[k2].equals("("))
				if (isSubSelect(as[k2 + 1])) {
					if (j2 < 16)
						j2++;
					ai2[j2] = 0;
				} else {
					ai2[j2]++;
				}
		}

		String as1[] = new String[3];
		as1[0] = "";
		as1[1] = "   ";
		as1[2] = sNewLine;
		StringBuffer stringbuffer = new StringBuffer();
		for (int l2 = 1; l2 < l - 1; l2++) {
			if (ai[l2 - 1] == 2)
				stringbuffer.append(repeatString(sIndent, ai1[l2]));
			stringbuffer.append(as[l2] + as1[ai[l2]]);
		}

		sql = stringbuffer.toString();
		for (int i3 = 0; i3 < vector.size(); i3++) {
			int j3 = sql.indexOf(" \001 ");
			if(j3!=-1){
				sql+= sql.substring(0, j3) ;
				sql+= (String) vector.get(i3);
				sql+= sql.substring(j3 + 1);
			}
			
		}

		sText = sql;
		if (bDebug) {
			StringBuffer stringbuffer1 = new StringBuffer();
			stringbuffer1.append(" Tokens:\r\n ");
			for (int k3 = 1; k3 < l - 1; k3++)
				stringbuffer1.append("" + ai1[k3] + "[" + as[k3] + "]"
						+ ai[k3] + " \r\n");

			stringbuffer1.append(" Escapes:\r\n ");
			for (int l3 = 0; l3 < vector.size(); l3++)
				stringbuffer1.append((String) vector.get(l3) + " \r\n");

			sDebugString = stringbuffer1.toString();
		}
		return this;
	}

	public void setNewLineBeforeAnd(boolean flag) {
		bNewLineBeforeAnd = flag;
	}

	public String getDebugString() {
		return sDebugString;
	}

	public void setNewLine(String s) {
		for (int i = 0; i < sEscapes.length; i++) {
			for (int j = 0; j < sEscapes[0].length; j++)
				if (sEscapes[i][j].equals(sNewLine))
					sEscapes[i][j] = s;

		}
		sNewLine = s;
	}

	SQLFormatter() {
		sText = "";
		sNewLine = "\r\n";
		sIndent = "   ";
		sDebugString = "";
		bCapitalizeMajor = false;
		bCapitalizeMinor = false;
		bCapitalizeNames = false;
		bNewLineBeforeAnd = true;
		bDebug = false;
	}

	private static boolean isMinor(String s) {
		return isIn(s,MINOR_WORDS);
	}

	private static boolean isIn(String s, String s1) {
		return s1.indexOf("|" + s.toUpperCase() + "|") > -1;
	}

	private static boolean isSubSelect(String s) {
		return isIn(s, SUB_SELECT);
	}

	public void setCapitalizeMajor(boolean flag) {
		bCapitalizeMajor = flag;
	}

	public void setCapitalizeNames(boolean flag) {
		bCapitalizeNames = flag;
	}

	public void setIndent(int i) {
		if (i < 0)
			sIndent = "\t ";
		else
			sIndent = repeatString("", i);
	}

	public void setDebug(boolean flag) {
		bDebug = flag;
	}

	private String repeatString(String s, int i) {
		if (i < 1)return "";
		StringBuffer stringbuffer = new StringBuffer(s.length() * i);
		for (int j = 0; j < i; j++)
			stringbuffer.append(s);

		return stringbuffer.toString();
	}
	public void setCapitalizeMinor(boolean flag) {
		bCapitalizeMinor = flag;
	}
	private static boolean isMajor(String s) {
		return isIn(s,MAJOR_WORDS);
	}
	public String getText() {
		return sText;
	}
	public SQLFormatter setText(String s) {
		sText = s;
		return this;
	}

	
}
