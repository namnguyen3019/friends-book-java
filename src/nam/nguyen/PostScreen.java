package nam.nguyen;

import java.util.ArrayList;
import java.util.Scanner;

public class PostScreen {

    private DataStorage d;
    private User user;

    public PostScreen(User user, DataStorage d) {
        this.d = d;
        this.user = user;
    }

    public void showPostScreen() {
        Scanner input = new Scanner(System.in);
        String choice = "";
        while (!choice.equals("x")) {
            System.out.println("===============");
            System.out.println("1. Show your current posts");
            System.out.println("2. Show a post detail");
            System.out.println("3. Create a post");
            System.out.println("4. Update a post");
            System.out.println("5. Delete a post");
            System.out.println("x. return");

            choice = input.next();

            switch (choice) {
                case "1":
                    showCurrentPosts(user, d);
                    break;
                case "2":
                    showPostById(user, d);
                    break;
                case "3":
                    createPost(user, d);
                    break;
                case "4":
                    updatePost(user, d);
                    break;
                case "5":
                    deletePost(user, d);
                    break;
                default:
                    break;
            }
        }
    }

    // Show current posts
    private void showCurrentPosts(User user, DataStorage d) {
        ArrayList<Post> currentPosts = user.getPosts(d);
        for (Post p : currentPosts) {
            System.out.println("'" + Integer.toString(p.getPost_id()) + "'. '" + p.getContent() + "'");
        }
    }

    // Show post by Id
    private void showPostById(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter post id");
        String post_id = input.next();

        Post post = user.getPostById(Integer.parseInt(post_id), d);
        System.out.println("post_id:  '" + post.getPost_id() + "' ");
        System.out.println("owner_id:  '" + post.getOwner_id() + "' ");
        System.out.println("content:  '" + post.getContent() + "' ");
        System.out.println("created_at:  '" + post.getCreated_at() + "' ");
        System.out.println("updated_at:  '" + post.getUpdated_at() + "' ");
    }

    // Create a new post
    private void createPost(User user, DataStorage d) {
        Scanner inputContent = new Scanner(System.in);
        String new_content = "";
        while (new_content.length() < 1) {
            System.out.println("Insert new content");
            new_content = inputContent.next();
        }
        Post new_post = new Post(new_content, user.getUsername());
        user.createPost(new_post, d);
    }

    // Update a post
    private void updatePost(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter post id");
        String post_id = input.next();
        System.out.println("Enter new content");
        String new_content = input.next();
        Post updatedPost = new Post(Integer.parseInt(post_id), new_content, user.getUsername());
        boolean isUpdated = user.updatePost(updatedPost, d);
        if (isUpdated) {
            System.out.println("The post '" + post_id + "' is updated");
        } else {
            System.out.println("Updated failed");
        }
    }

    // Delete a post
    private void deletePost(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a post_id");
        String post_id = input.next();
        System.out.println("Are you sure to delete this post '" + post_id + "', (y/n):");
        String answer = input.next();
        if (answer.toLowerCase().equals("y")) {
            boolean isDeleteSuccess = user.deletePost(Integer.parseInt(post_id), user.getUsername(), d);
            if (isDeleteSuccess) {
                System.out.println("Done!");
            } else {
                System.out.println("Failed!");
            }
        }
    }
}

// if (choice.equals("1")) {
// user.getPosts(d);
// } else if (choice.equals("2")) {
// showPostById(user, d);
// } else if (choice.equals("3")) {
// createPost(user, d);
// } else if (choice.equals("4")) {
// updatePost(user, d);
// } else if (choice.equals("5")) {
// deletePost(user, d);
// }
// }