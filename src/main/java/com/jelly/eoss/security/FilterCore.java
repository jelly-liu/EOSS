package com.jelly.eoss.security;

/**
 * Created by jelly on 2016-10-25.
 */

import com.google.common.io.LineReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 /index.html = anon
 /user/create = anon
 /user/** = authc
 /admin/** = authc, roles[administrator]
 /rest/** = authc, rest
 /remoting/rpc/** = authc, perms[remote:invoke]
 */
public class FilterCore {
    private static final Logger log = LoggerFactory.getLogger(FilterCore.class);
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    public void initRuleByFilterDefinition(String filterDefinition){
        LineReader lineReader = null;
        try {
            new LineReader(new StringReader(filterDefinition));
            String line = null;
            while((line = lineReader.readLine()) != null){
                line = StringUtils.trimToNull(line);
                if(line == null){
                    continue;
                }

                String[] lineSegment = StringUtils.split(line, "=");
                if(lineSegment == null || lineSegment.length != 2){
                    log.debug("can not find = in line={}", line);
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
