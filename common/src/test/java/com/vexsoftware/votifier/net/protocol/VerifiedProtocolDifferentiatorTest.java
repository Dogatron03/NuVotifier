package com.vexsoftware.votifier.net.protocol;

import com.vexsoftware.votifier.net.VotifierSession;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

public class VerifiedProtocolDifferentiatorTest {
    @Test
    public void v1Test() {
        EmbeddedChannel channel = new EmbeddedChannel(new VotifierProtocolDifferentiator(true));

        VotifierSession session = new VotifierSession();
        channel.attr(VotifierSession.KEY).set(session);

        ByteBuf test = Unpooled.buffer(256);
        for (int i = 0; i < 256; i++) {
            test.writeByte(0);
        }
        channel.writeInbound(test);

        assertEquals(VotifierSession.ProtocolVersion.ONE, session.getVersion());
        test.release();
        channel.close();
    }

    @Test
    public void v2Test() {
        EmbeddedChannel channel = new EmbeddedChannel(new VotifierProtocolDifferentiator(true));

        VotifierSession session = new VotifierSession();
        channel.attr(VotifierSession.KEY).set(session);

        ByteBuf test = Unpooled.buffer();
        test.writeShort(0x733A);
        channel.writeInbound(test);

        assertEquals(VotifierSession.ProtocolVersion.TWO, session.getVersion());
        test.release();
        channel.close();
    }
}