package org.example.bookservice.cloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "library-service", url = "http://library-service:8081")
public interface LibraryServiceClient {
    @PostMapping("/api/v1/library/register")
    void registerBook(@RequestParam Long bookId);
}
