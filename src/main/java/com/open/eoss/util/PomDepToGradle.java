package com.open.eoss.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

public class PomDepToGradle {
    public static void main(String[] args) throws Exception {
        String pom_xml = "/Users/jelly/Documents/idea-workspace/eoss-ee/pom.xml";
        File input = new File(pom_xml);
        Document doc = Jsoup.parse(input, "UTF-8");
        Elements dependencies = doc.select("dependencies dependency");
        System.out.println("dependencies size: " + dependencies.size());

        for(Element dependency : dependencies){
            String groupId = dependency.select("groupId").text();
            String artifactId = dependency.select("artifactId").text();
            String version = dependency.select("version").text();
            System.out.println("implementation " + "\"" + groupId + ":" + artifactId + ":" + version + "\"");
        }
    }
}
