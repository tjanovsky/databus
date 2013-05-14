package controllers.gui;

import gov.nrel.util.Utility;

import javax.persistence.Embedded;

import models.EntityUser;
import models.UserSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alvazan.play.NoSql;

import play.mvc.Controller;
import play.mvc.With;
import controllers.gui.auth.GuiSecure;

@With(GuiSecure.class)
public class MySettings extends Controller {
	private static final Logger log = LoggerFactory.getLogger(MySettings.class);
	
	@Embedded
	private UserSettings settings;
	
	public static void mySettings() {
		EntityUser user = Utility.getCurrentUser(session);
		
		// Set current options
		renderArgs.put("dashboard_enabled", user.getUserSettings().getDashboardEnabled());
		renderArgs.put("dashboard_chart_count", user.getUserSettings().getDashboardChartCount());
		
		renderArgs.put("user_chart_1", user.getUserSettings().getDashboardChart_1());
		renderArgs.put("user_chart_2", user.getUserSettings().getDashboardChart_2());
		renderArgs.put("user_chart_3", user.getUserSettings().getDashboardChart_3());
		renderArgs.put("user_chart_4", user.getUserSettings().getDashboardChart_4());
		
		render(user);
	}
	
	public static void postSaveAccountSettings(String dashboard_enabled, String dashboard_chart_count, String dashboard_chart1_select,
																	 String dashboard_chart2_select, String dashboard_chart3_select, String dashboard_chart4_select,
																	 String dashboard_chart5_select, String dashboard_chart6_select, String dashboard_chart7_select,
																	 String dashboard_chart8_select, String dashboard_chart9_select, String dashboard_chart10_select) {
		EntityUser user = Utility.getCurrentUser(session);
		UserSettings userSettings = user.getUserSettings();
				
		String enabled = "true";
		if(dashboard_enabled == null) {
			enabled = "false";
		}
		userSettings.setDashboardEnabled(enabled);
		userSettings.setDashboardChartCount(dashboard_chart_count);
		
		String dashboardChart_1 = dashboard_chart1_select;
		String dashboardChart_2 = "BLANK";
		String dashboardChart_3 = "BLANK";
		String dashboardChart_4 = "BLANK";
		
		if(dashboard_chart_count.endsWith("2")) {
			dashboardChart_1 = dashboard_chart2_select;
			dashboardChart_2 = dashboard_chart3_select;
		} else if(dashboard_chart_count.endsWith("3")) {
			dashboardChart_1 = dashboard_chart4_select;
			dashboardChart_2 = dashboard_chart5_select;
			dashboardChart_3 = dashboard_chart6_select;
		} else if(dashboard_chart_count.endsWith("4")) {
			dashboardChart_1 = dashboard_chart7_select;
			dashboardChart_2 = dashboard_chart8_select;
			dashboardChart_3 = dashboard_chart9_select;
			dashboardChart_4 = dashboard_chart10_select;
		}
		
		userSettings.setDashboardChart_1(dashboardChart_1);
		userSettings.setDashboardChart_2(dashboardChart_2);
		userSettings.setDashboardChart_3(dashboardChart_3);
		userSettings.setDashboardChart_4(dashboardChart_4);
		
		/**
		 * To Save:
		 * 		
		 * 		NoSql.em().put(user);
		 * 		NoSql.em().flush();
		 */
		NoSql.em().put(user);
		NoSql.em().flush();		
		
		mySettings();
	}

}