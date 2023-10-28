package tech.bonda.sufest2023.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.services.BookmarksService;
import tech.bonda.sufest2023.services.ClientVersionService;

@RestController
@RequestMapping("/clientVersion")
@CrossOrigin("*")
@AllArgsConstructor
public class ClientVersionController {

    private final ClientVersionService clientVersionService;

    private final BookmarksService bookmarksService;

    @GetMapping("/searchCompany/{keyword}")
    public ResponseEntity<?> searchCompanyByKeyWord(@PathVariable String keyword) {
        System.out.println("keyword = " + keyword);
        return clientVersionService.searchCompanyByKeyWord(keyword);
    }

    @PostMapping("/bookmark/{id}")
    public ResponseEntity<?> saveBookmark(@PathVariable Integer id, @RequestBody String bookmark) {
        return bookmarksService.saveBookmark(id, Integer.valueOf(bookmark));
    }

    @GetMapping("/getBookmarks/{id}")
    public ResponseEntity<?> getBookmarks(@PathVariable Integer id) {
        return bookmarksService.getBookmarks(id);
    }

    @DeleteMapping("/deleteBookmark/{id}/{bookmarkToDelete}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Integer id, @PathVariable String bookmarkToDelete) {
        return bookmarksService.deleteBookmark(id, Integer.valueOf(bookmarkToDelete));
    }
}
