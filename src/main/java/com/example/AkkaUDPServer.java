package com.example;


import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.alpakka.udp.Datagram;
import akka.stream.alpakka.udp.javadsl.Udp;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletionStage;

public class AkkaUDPServer {
      public static void main(String[] args) {
          final ActorSystem system = ActorSystem.create("udp-server");
          final InetSocketAddress socket = new InetSocketAddress("127.0.0.1", 12345);

          //create my source - for UDP, it's a flow with input (the incoming UDP datagrams from source IP) and output (the outgoing UDP datagrams to the source IP)
          final Flow<Datagram,Datagram,CompletionStage<InetSocketAddress>> bindFlow = Udp.bindFlow(socket,system);  //returns the UDP flow

          // Your stdoutSink
          final Sink printsink = Sink.foreach(dg -> System.out.println("Data received: " + dg));

          final Source ignoresource = Source.maybe();
          final RunnableGraph<NotUsed> runnable = ignoresource.via(bindFlow).map(dg -> dg.toString()).to(printsink);
          runnable.run(system);
      }
}
