package com.yxbjll.middleware.netty.nettyDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import javafx.util.Builder;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author yxbjll
 * @desc ServerHandler
 * @since 2020/3/3
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {


	public void channelRead(ChannelHandlerContext ctx,Object msg) throws UnsupportedEncodingException {

		//do something msg
		ByteBuf buf = null;
		try {
			if (msg instanceof HttpRequest){
				/*ByteBuf buf = (ByteBuf)msg;
				byte[] data = new byte[buf.readableBytes()];
				buf.readBytes(data);
				String request = new String(data, "utf-8");
				System.out.println("Server: " + request);*/
				//写给客户端
				//ctx.writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));


				HttpRequest request = (HttpRequest) msg;
				msg = Unpooled.copiedBuffer(request.getHeaders().getCacheControl().getBytes());
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
				//3. 设置返回的数据头信息
				response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
				response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
				ctx.writeAndFlush(response);
			}else if (msg instanceof ByteBuf){
				buf = (ByteBuf)msg;
				if (buf.readableBytes() > 0){
					String str = buf.toString(CharsetUtil.UTF_8);
					System.out.println(str);
					String response = "接收消息："+str;
					//System.out.println(String.format(response,str));
					System.out.println(response);
				}
			}
		}finally {
			ReferenceCountUtil.release(buf);
		}
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}



}
