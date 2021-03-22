#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <poll.h>
#include <unistd.h>
#include <sys/time.h>


#define MAX_POLLFD_LEN 4096  
#define PORT 9108


void server() {
	
	// 创建socket连接
	int lfd = socket(AF_INET,SOCK_STREAM,0);
	struct sockaddr_in my_addr; 
	bzero(&my_addr, sizeof(my_addr));
	my_addr.sin_family = AF_INET; // ipv4
	my_addr.sin_port   = htons(PORT);
	my_addr.sin_addr.s_addr = htonl(INADDR_ANY); 
	// 绑定端口
	bind(lfd, (struct sockaddr*)&my_addr, sizeof(my_addr));
	// 监听连接请求
	listen(lfd, 128);
	printf("listen client @port=%d...\n",PORT);
	
	// 定义pollfd对象
	struct pollfd fds[MAX_POLLFD_LEN];
	memset(fds, 0, sizeof(fds));
	// 添加socket服务监听
	fds[0].fd = lfd;
	fds[0].events = POLLIN;
	int nfds = 1;
	int i;
	for(i = 1; i < MAX_POLLFD_LEN; i++) {
		fds[i].fd = -1;
	}
	int maxFds = 0;
	printf("准备进入while循环\n");
	while (1) {
		printf("阻塞中, [maxFds=%d]...\n", maxFds);
		int nready = poll(fds, maxFds + 1, -1);
		switch (nready) {
			case 0 :
				printf("select time out ......\n");
				break;
			case -1 :
				perror("select error \n");
				break;
			default:
				// 监听到新的客户端连接
				if (fds[0].revents & POLLIN) {
					struct sockaddr_in client_addr;	
					socklen_t cliaddr_len = sizeof(client_addr);
					char cli_ip[INET_ADDRSTRLEN] = "";	
					// 肯定有连接不会阻塞
					int clientfd = accept(lfd, (struct sockaddr*)&client_addr, &cliaddr_len);
					inet_ntop(AF_INET, &client_addr.sin_addr, cli_ip, INET_ADDRSTRLEN);
					printf("----------------------------------------------\n");
					printf("client ip=%s,port=%d\n", cli_ip, ntohs(client_addr.sin_port));
					// 将clientfd加入读集合
					int j;
					for (j = 1; j < MAX_POLLFD_LEN; ++j) {
						if (fds[j].fd < 0) {
							fds[j].fd = clientfd;
							fds[j].events = POLLIN;
							printf("添加客户端成功...\n");
							maxFds++;   
							break;
						}
						if(j == MAX_POLLFD_LEN){
							printf("too many clients"); 
							exit(1);
						}
						
					}
				    
					if(--nready <= 0) {
						continue;
					}
				}
				int i;
				printf("maxFds=%d\n", maxFds);
				for (i = 1; i <= maxFds; i++) {
					printf("i=%d\n", i);
					// 处理读事件
					if (fds[i].revents & POLLIN) {
						int sockfd = fds[i].fd;
						char recv_buf[512] = "";
						int rs = read(sockfd, recv_buf, sizeof(recv_buf));

						if (rs == 0) {
							close(sockfd);
							fds[i].fd = -1;
						} else {
							printf("%s\n",recv_buf);
							// 给每一个服务端写数据
							int j;
							for (j = 1;j <= maxFds; j++) {
								if (j != i) {
									write(fds[j].fd, recv_buf, strlen(recv_buf));
								}
							}
						}
					}
				}
		}
		
	}
}

int main(){
	server();
	return 0;
}
