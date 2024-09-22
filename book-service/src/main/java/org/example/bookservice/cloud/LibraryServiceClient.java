package org.example.bookservice.cloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "library-service", url = "http://localhost:8081")
public interface LibraryServiceClient {
    @PostMapping("/library/register")
    void registerBook(@RequestParam Long bookId);
}
