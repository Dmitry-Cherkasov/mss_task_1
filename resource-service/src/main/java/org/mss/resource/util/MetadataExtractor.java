package org.mss.resource.util;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;

public class MetadataExtractor{

    public static Metadata  extractMetadata(byte[] data) {
        Metadata metadata = new Metadata();
        BodyContentHandler handler = new BodyContentHandler();
        ParseContext pContext = new ParseContext();
        Tika tika = new Tika();
        try (ByteArrayInputStream input = new ByteArrayInputStream(data)){
            Mp3Parser mp3Parser = new  Mp3Parser();
            mp3Parser.parse(input, handler, metadata, pContext);
            tika.parse(input, metadata);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse metadata");
        }
        return metadata;
    }
}
