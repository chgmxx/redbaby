package com.bage.redbaby.parser;

import java.util.List;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bage.redbaby.domain.Topic;
import com.bage.redbaby.net.BaseParser;
/**
 * {
  "response": "topic",
  " topic ": [
{
  "id": "专题活动ID",
      "name": "专题活动名称",
      "pic": "图片URL"
    },
{
  "id": "专题活动ID",
      "name": "专题活动名称",
      "pic": "图片URL"
    }
  ]
}

 * @author fada
 *
 */
public class TopicParser extends BaseParser<List<Topic>> {

	@Override
	public List<Topic> parseJSON(String str) {
		try {
			JSONObject jsonObject = new JSONObject(str);
			String topicsString = jsonObject.getString("topic");
			List<Topic> topics = JSON.parseArray(topicsString, Topic.class);
			return topics;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
