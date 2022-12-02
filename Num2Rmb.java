import java.util.Arrays;
import java.io.*;
/**
 * Date:2/12/2022<br>
 * @author jinniu
 * @version 1.0
 */
public class Num2Rmb
{
	private String[] hanArr = {"零", "壹", "贰", "叁", "肆",
		"伍", "陆", "柒", "捌", "玖"};
	private String[] unitArr = {"十", "百", "千"};

	/**
	 * 把一个浮点数分解成整数部分和小数部分字符串
	 * @param num 需要被分解的浮点数
	 * @return 分解出来的整数部分和小数部分。第一个数组元素是整数部分，第二个数组元素是小数部分。
	 */
	private String[] divide(double num)
	{
		// 将一个浮点数强制类型转换为long，即得到它的整数部分
		var zheng = (long) num;
		// 浮点数减去整数部分，得到小数部分，小数部分乘以100后再取整得到2位小数
		var xiao = Math.round((num - zheng) * 100);
		// 下面用了2种方法把整数转换为字符串
		return new String[] {zheng + "", String.valueOf(xiao)};
	}

	/**
	 * 把一个四位的数字字符串变成汉字字符串
	 * @param numStr 需要被转换的四位的数字字符串
	 * @return 四位的数字字符串被转换成的汉字字符串。
	 */
	private String toHanStr(String numStr)
	{
		var result = "";
		int numLen = numStr.length();
		//index用来处理四位字符串从尾部到头部存在连续1-4个0的情况
		int index = numLen;
		// index默认是4，最后一位开始到第0位，联系几个0则index减几。
		for (var j = numLen - 1; j > -1; j--)
		{
			if (numStr.charAt(j) == '0')
			{
				index--;
			}
			else
			{
				break;
			}
		}
		//用index控制最后连续为0则不用运算了
		for (var i = 0; i < index; i++)
		{
			// 把char型数字转换成的int型数字，因为它们的ASCII码值恰好相差48
			// 因此把char型数字减去48得到int型数字，例如'4'被转换成4。
			var num = numStr.charAt(i) - 48;
			// 如果不是最后一位数字，而且数字不是零，则需要添加单位（千、百、十）
			if (i != numLen - 1 && num != 0)
			{
				result += hanArr[num] + unitArr[numLen - 2 - i];
			}
			// 否则不要添加单位
			else
			{   
				if ((i > 0)&&(numStr.charAt(i-1) - 48 ==0)&&(num == 0)) continue;
				result += hanArr[num];
			}
		}
		return result;
	}
	/**
	 * 把一个两位的数字字符串变成汉字字符串
	 * @param numStr 需要被转换的两位小数的数字字符串
	 * @return 两位小数对应的汉字字符串。
	 */
	private String  countxiao(String xiaoshu)
	{
		var jiaonum = xiaoshu.charAt(0) - 48;
		var fennum = xiaoshu.charAt(1) - 48;
		var xiaoresult = hanArr[jiaonum] + "角" + hanArr[fennum] + "分" + "";
		return xiaoresult;
	}

	public static void main(String[] args) throws Exception
	{
		var nr = new Num2Rmb();
		System.out.println("输入你需要转行的金额并回车：");
		
	    // 这是用于获取键盘输入的方法
		var br = new BufferedReader(new InputStreamReader(System.in));
		String inputStr = null;
		// br.readLine()：每当在键盘上输入一行内容按回车，用户刚输入的内容将被br读取到。
		while ((inputStr = br.readLine()) != null)
		{
		// 将用户输入的字符串转成double型		
		double a = Double.parseDouble(inputStr);
		String[] jieguo = nr.divide(a);
		var zhengresult = "";
		var xiaoresult = "";
		String zhengshu = jieguo[0];
		String xiaoshu  = jieguo[1];
		//最多计算千亿级别的数字
		if (zhengshu.length() > 12)
		{
			System.out.println("数值过大");
		}
		else 
		{
			 var len = zhengshu.length();
			 if (len <= 4)		 
			 {
				zhengresult = nr.toHanStr(zhengshu.substring(0)) + "元";
//				System.out.println(zhengresult);
			 }
			 if (4 < len&&len <= 8)
			 {
				 zhengresult = nr.toHanStr(zhengshu.substring(0, len - 4))+"万" + nr.toHanStr(zhengshu.substring(len - 4)) + "元";
//				 System.out.println(zhengresult);
		     }
			 if (8 < len&& len <=12)
		     {
//				System.out.println(zhengshu.substring(0, len - 8));
				System.out.println(zhengshu.substring(len - 8 , len - 4));
				var str1 = zhengshu.substring(len - 8 , len - 4);
				var str2 = "0000";
//				System.out.println(zhengshu.substring(len - 4 ));
                //这段代码用来排除100 0000 9090这种情况 中文字符串中间的万字根本不用显示的
				if (str1.equals(str2))
				{
				zhengresult = nr.toHanStr(zhengshu.substring(0, len - 8))+"亿" + nr.toHanStr(zhengshu.substring(len - 4 )) + "元";
				}
				else
				{
				zhengresult = nr.toHanStr(zhengshu.substring(0, len - 8))+"亿" + nr.toHanStr(zhengshu.substring(len - 8 , len - 4))+"万" + nr.toHanStr(zhengshu.substring(len - 4 )) + "元";
                }
//				System.out.println(zhengresult);
			 }
		}
		xiaoresult = nr.countxiao(xiaoshu);
		System.out.println(zhengresult + xiaoresult);
		System.out.println();
		System.out.println();
		System.out.println("输入你需要转行的金额并回车：");
        }
	}
}
