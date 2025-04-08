-- Remove the table if it exists (for development)
DROP TABLE IF EXISTS code_file;

-- Create the table with proper schema
CREATE TABLE code_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    filename VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- Then insert your data
INSERT INTO code_file (filename, content, created_at)
VALUES ('HelloWorld.java', 'public class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello, World!");
  }
}', CURRENT_TIMESTAMP);