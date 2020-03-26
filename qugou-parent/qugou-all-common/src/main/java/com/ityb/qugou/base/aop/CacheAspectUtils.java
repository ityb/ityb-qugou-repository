package com.ityb.qugou.base.aop;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;
import com.ityb.qugou.base.utils.ReflectionUtils;

public class CacheAspectUtils {
	
	public static String analysisDescription(String description,Object[] args) {
		if(StringUtils.isEmpty(description)) {
			return null;
		}
		Pattern p=Pattern.compile("(<[^>]*)");
		Matcher m=p.matcher(description);
		List<String> result=new ArrayList<>();
		while(m.find()) {
			result.add(m.group());
		}
		for (String param1 : result) {
			String pm=param1;
			pm=pm.replaceAll("<","").replaceAll(">", "").replaceAll("：", ":");
			String[] params=pm.split("\\s*:\\s*");
			if(params.length==1) {//表示以第一个参数作为key
				Integer index=Integer.valueOf(Integer.parseInt(params[0]));
				Object obj=args[index.intValue()];
				description=description.replace(param1, obj==null?"":obj.toString());
			}else {
				Integer index=Integer.valueOf(Integer.parseInt(params[0]));
				String field=params[1];
				Object o=args[index.intValue()];
				if(o==null) {
					description=description.replace(param1, "");
				}else {
					Object fieldValue;
					try {
						fieldValue = ReflectionUtils.getFieldValue(o, field);
						String value=(fieldValue==null?"":fieldValue.toString());
						description=description.replaceAll(param1, value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return description;
	}
}
