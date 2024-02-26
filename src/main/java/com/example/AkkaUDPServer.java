package com.example;


import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.alpakka.udp.Datagram;
import akka.stream.alpakka.udp.javadsl.Udp;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletionStage;

public class AkkaUDPServer {
      public static void main(String[] args) {
          final ActorSystem system = ActorSystem.create("udp-server");
          final InetSocketAddress socket = new InetSocketAddress("127.0.0.1", 12346);

          //create my source - for UDP, it's a flow with input (the incoming UDP datagrams from source IP) and output (the outgoing UDP datagrams to the source IP)
          final Flow<Datagram,Datagram,CompletionStage<InetSocketAddress>> bindFlow = Udp.bindFlow(socket,system);  //returns the UDP flow

          // Your stdoutSink
          final Sink printsink = Sink.foreach(item -> {
              if (item instanceof Datagram) {
                  Datagram dg = (Datagram) item;
                  System.out.println("Data received: " + dg.getData());
              } else {
                  // Handle other types if needed
                  System.out.println("Received an unexpected type: " + item.getClass());
              }
          });

          final Source ignoresource = Source.maybe();
          final RunnableGraph<NotUsed> runnable = ignoresource.via(bindFlow).to(printsink);
          runnable.run(system);

          try {
              System.out.println(">>> Press ENTER to exit <<<");
              System.in.read();
          } catch (IOException ignored) {
          } finally {
              system.terminate();
          }
      }
}
