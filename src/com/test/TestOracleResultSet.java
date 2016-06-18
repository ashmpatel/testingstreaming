package com.test;

import java.sql.ResultSet;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by ash on 18/06/16.
 */
public class TestOracleResultSet {

    public static Stream<String> recordsetStream(ResultSet in) {

        final ScrollableResultsSpliterator<String> cr = new ScrollableResultsSpliterator(String.class,10,in);

        return StreamSupport.stream(cr, false).onClose(() -> {
                cr.close();
        });
    }

    /*
   public Response test() {
       StreamingOutput stream = new StreamingOutput() {
           @Override
           public void write(OutputStream os) throws IOException, WebApplicationException {
               Writer writer = new BufferedWriter(new OutputStreamWriter(os));

               for (org.neo4j.graphdb.Path path : paths) {
                   writer.write(path.toString() + "\n");
               }
               writer.flush();
           }

           return Response.ok(stream).

           build();
       };

   }

    @GET
    @Path("/pdf")
    public Response downloadPdfFile()
    {
        StreamingOutput fileStream =  new StreamingOutput()
        {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException
            {
                try
                {
                    java.nio.file.Path path = Paths.get("C:/temp/test.pdf");
                    byte[] data = Files.readAllBytes(path);
                    output.write(data);
                    output.flush();
                }
                catch (Exception e)
                {
                    throw new WebApplicationException("File Not Found !!");
                }
            }
        };
        return Response
                .ok(fileStream, PageAttributes.MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename = myfile.pdf")
                .build();
    }
*/

}
