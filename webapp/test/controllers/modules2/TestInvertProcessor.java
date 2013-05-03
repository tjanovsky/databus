package controllers.modules2;

import gov.nrel.util.StartupGroups;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import robot.Utility;

public class TestInvertProcessor {

	@Test
	public void testProc() throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String requestUri = "/api/invertV1/rawdataV1/fakeTimeSeries2/15500/16000";
		String theString = Utility.sendRequest(httpclient, requestUri, StartupGroups.ROBOT_USER, StartupGroups.ROBOT_KEY);
		
		ObjectMapper mapper = new ObjectMapper();		
		Map root = mapper.readValue(theString, Map.class);
		List rows = (List) root.get("data");
		Assert.assertEquals(7, rows.size());
		Map<String, Integer> dataPoint = (Map<String, Integer>) rows.get(0);
		Assert.assertEquals(new Integer(15531), dataPoint.get("time"));
		Assert.assertEquals(new Integer(-193), dataPoint.get("value"));
		
		Map<String, Integer> dataPoint2 = (Map<String, Integer>) rows.get(rows.size()-1);
		Assert.assertEquals(new Integer(15891), dataPoint2.get("time"));
		Assert.assertEquals(new Integer(-199), dataPoint2.get("value"));		
	}
}
