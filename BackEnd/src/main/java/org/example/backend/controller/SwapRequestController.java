package org.example.backend.controller;

import org.example.backend.dto.SwapRequestDTO;
import org.example.backend.service.SwapRequestService;
import org.example.backend.utill.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/swap")
public class SwapRequestController {

    @Autowired
    private SwapRequestService swapRequestService;

    @PostMapping("/send")
    public ResponseEntity<APIResponse<String>> sendRequest(@RequestBody SwapRequestDTO dto) {
        swapRequestService.sendRequest(dto);
        return new ResponseEntity<>(new APIResponse<>(201, "Swap Request Sent Successfully", null), HttpStatus.CREATED);
    }

    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<APIResponse<String>> updateStatus(@PathVariable Long id, @PathVariable String status) {
        swapRequestService.updateStatus(id, status);
        return new ResponseEntity<>(new APIResponse<>(200, "Status Updated to " + status, null), HttpStatus.OK);
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<APIResponse<List<SwapRequestDTO>>> getPending(@PathVariable Long userId) {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", swapRequestService.getRequestsForUser(userId)), HttpStatus.OK);
    }
}