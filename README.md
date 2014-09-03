# Introduction

This is a simple example application to read the (partial, randomized) twitter data stream for a given list of keywords.

Example output

    2014-09-03 20:39:42.022 [      main] DEBUG - Loading configuration
    2014-09-03 20:39:42.103 [      main] DEBUG - Setting configuration options.
    2014-09-03 20:39:42.133 [      main] INFO  - keywords=[#twitter, #api]
    2014-09-03 20:39:42.147 [      main] INFO  - consumerkey=...
    2014-09-03 20:39:42.147 [      main] DEBUG - Building client.
    2014-09-03 20:39:42.258 [      main] INFO  - Connecting to twitter.
    2014-09-03 20:39:42.260 [      main] INFO  - Starting to process messages.
    2014-09-03 20:39:44.238 [      main] INFO  - Id: 507236515504611328, User: @priyabubbs, Message: Fall Season is Conference Season http://t.co/dMwZRQj8S9 #API #php #integration #techconference
    2014-09-03 20:39:51.705 [      main] INFO  - Id: 507236546915737600, User: @allwynstanley, Message: We know that we can save time by staying away from #twitter ...but NO ! #wearerebels
    2014-09-03 20:39:52.447 [      main] INFO  - Id: 507236549688586240, User: @FiOS1NewsNJ, Message: RT @NancyWardTV: Welcome #meteorologist  @JamieBoothWX to #twitter!! Tune in everyday only on @FiOS1NewsNJ @FiOS1News_LI http://t.co/6zr5k7…
    2014-09-03 20:39:52.461 [      main] INFO  - Id: 507236550363869184, User: @RyanFloresd, Message: @LydellLoins You wanna know how to get 100,000 Real #Twitter Followers? Watch this tutorial http://t.co/WLewlwMHDO
    2014-09-03 20:39:55.185 [      main] INFO  - Id: 507236561893994496, User: @GimmeSkye, Message: Photo: It’s #AssWednesday - Follow Me @GimmeSkye on #Twitter &amp; #GimmeSkye / #TheSkyeJettLounge on #Tumblr... http://t.co/72G7elr9Qq
    2014-09-03 20:40:06.811 [      main] INFO  - Id: 507236610195615744, User: @La_PajOnuuaa, Message: RT @Frequito02: -' En Mi #Twitter Todas Tienen Novioo  -_-' 😂😂?

# Configuration

Insert your developer- and application-specific values for

    consumerKey=...
    consumerSecret=...
    token=...
    secret=...

and add your keywords to

    keywords=


# TODO

- Add database (Hbase) to allow multiple connections (to gather more data), tweet id should be the primary key.