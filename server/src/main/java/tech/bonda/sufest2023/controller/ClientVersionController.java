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

    @GetMapping("/companies/{keyword}")
    public ResponseEntity<?> searchCompaniesByKeyword(@PathVariable String keyword) {
        return clientVersionService.searchCompanyByKeyWord(keyword);
    }

    @PostMapping("/bookmark/{userId}")
    public ResponseEntity<?> saveBookmark(@PathVariable Integer userId, @RequestBody String bookmark) {
        return bookmarksService.saveBookmark(userId, Integer.valueOf(bookmark));
    }

    @GetMapping("/bookmarks/{userId}")
    public ResponseEntity<?> getBookmarks(@PathVariable Integer userId) {
        return bookmarksService.getBookmarks(userId);
    }

    @DeleteMapping("/bookmarks/{userId}/{bookmarkId}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Integer userId, @PathVariable String bookmarkId) {
        return bookmarksService.deleteBookmark(userId, Integer.valueOf(bookmarkId));
    }
}
