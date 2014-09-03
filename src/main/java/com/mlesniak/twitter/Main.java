package com.mlesniak.twitter;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    private Configuration config;
    private final Gson parser;

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    public Main() throws ConfigurationException {
        log.debug("Loading configuration");
        URL configFile = getClass().getResource("/application.properties");
        config = new PropertiesConfiguration(configFile);
        parser = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    private void run(String... args) throws Exception {
        // Store values.
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

        // Host, endpoint (everything), and authentication.
        log.debug("Setting configuration options.");
        Hosts host = new HttpHosts(Constants.STREAM_HOST);

        // Define keywords to track.
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        log.info("keywords={}", config.getList("keywords"));
        List<String> terms = new ArrayList<String>();
        for (Object keyword : config.getList("keywords")) {
            terms.add(keyword.toString());
        }
        endpoint.trackTerms(terms);

        Authentication auth = new OAuth1(
                config.getString("consumerKey"),
                config.getString("consumerSecret"),
                config.getString("token"),
                config.getString("secret"));
        log.info("consumerkey={}, consumerSecret={}, token={}, secret={}",
                config.getString("consumerKey"),
                config.getString("consumerSecret"),
                config.getString("token"),
                config.getString("secret"));

        log.debug("Building client.");
        ClientBuilder builder = new ClientBuilder()
                .name("Example client")
                .hosts(host)
                .authentication(auth)
                .endpoint(endpoint)
                .processor(new StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue);
        Client client = builder.build();

        log.info("Connecting to twitter.");
        client.connect();

        processMessages(client, msgQueue, eventQueue);
    }

    private void processMessages(Client client, BlockingQueue<String> msgQueue, BlockingQueue<Event> eventQueue)
            throws InterruptedException {
        log.info("Starting to process messages.");
        while (!client.isDone()) {
            String json = msgQueue.take();
            Message message = parser.fromJson(json, Message.class);
            log.trace("Original: {}", json.substring(0, json.length() - 1));
            // Filter newlines.
            String text = message.getText().replaceAll("\\n", " ");
            log.info("Id: {}, User: @{}, Message: {}",
                    message.getId(),
                    message.getUser().getScreenName(),
                    text);
        }
        log.info("Finished message processing.");
    }
}
