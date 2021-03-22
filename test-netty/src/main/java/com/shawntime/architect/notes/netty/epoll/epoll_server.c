#include<stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>						
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>	
#include <sys/epoll.h>

void server() {
	
	// 创建socket连接
	int lfd = socket(AF_INET,SOCK_STREAM,0);
	struct sockaddr_in my_addr; 
	bzero(&my_addr, sizeof(my_addr));
	my_addr.sin_family = AF_INET; // ipv4
	my_addr.sin_port   = htons(8088);
	my_addr.sin_addr.s_addr = htonl(INADDR_ANY); 
	// 绑定端口
	bind(lfd, (struct sockaddr*)&my_addr, sizeof(my_addr));
	// 监听连接请求
	listen(lfd, 128);
	printf("listen client @port=%d...\n", 8088);
	int epct, i;
	struct epoll_event event;
	struct epoll_event events[100];
	memset(events, 0, 100 * sizeof(struct epoll_event));
	int epfd = epoll_create(1);
	event.data.fd = lfd;
	event.events = EPOLLIN;
	epoll_ctl(epfd, EPOLL_CTL_ADD, lfd, &event);
	while (1) {
		printf("阻塞中....\n");
		int nready = epoll_wait(epfd, events, 20, -1);
		int i;
		for (i = 0; i < nready; ++i) {
			// 监听到新的客户端连接
			if (events[i].data.fd == lfd) {
				struct sockaddr_in client_addr;	
				socklen_t cliaddr_len = sizeof(client_addr);
				char cli_ip[INET_ADDRSTRLEN] = "";	
				// 肯定有连接不会阻塞
				int clientfd = accept(lfd, (struct sockaddr*)&client_addr, &cliaddr_len);
				inet_ntop(AF_INET, &client_addr.sin_addr, cli_ip, INET_ADDRSTRLEN);
				
				event.data.fd = clientfd;
				event.events = EPOLLIN | EPOLLET;
				epoll_ctl(epfd, EPOLL_CTL_ADD, clientfd, &event);
				
				printf("----------------------------------------------\n");
				printf("client ip=%s,port=%d\n", cli_ip, ntohs(client_addr.sin_port));
			} else {
				char recv_buf[512] = "";
				int rs = read(events[i].data.fd, recv_buf, sizeof(recv_buf));
				if (rs < 0) {
					close(events[i].data.fd);
					epoll_ctl(epfd, EPOLL_CTL_DEL, events[i].data.fd, &event);
					continue;
				}
				printf("%s\n",recv_buf);
			}
		}
	
		
	}
}

int main(){
	server();
	return 0;
}
