package tests.restAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;


public class RestAssuredAPITest {

    static String accessToken = "BQAsUG2PKHxE5ckisSJnQiCMzW5i8cu-86Oq6fRPONg1xWbJc4HKVNJb-G3RpjdEGXm6Ky68Ve33bhj4d_Nq1yGQRl1PFohrhx5e5OuCjGEO5TdfKXEDRwzv0T8TtismJ1CU9Ab_dbVfsKfPvngux9a3uCOqfz7LjrOtkr1tpOpF-XaEcwq6dLwW7a3UX6kCPcXx15pLLpcaX86MOdNPHWKJkCFrI9oj6_ZeNTF-_vsWxvwchODOUnp8xK6A5Hg7IJIvxSpzTiD76XWUGsPQlTJe5g5gqs39SipTB-B3L38Efz5dcw-N4KQ1QyTZZon5FDiAKAvPyd_hYg";



        //Get artist
        @Test
        public static void getArtist(){

            ValidatableResponse response = RestAssured.given().contentType(ContentType.JSON)
                    .baseUri("https://api.spotify.com/").basePath("v1")
                    .accept(ContentType.JSON).header("Authorization", "Bearer " + accessToken)
                    .when()
                    .get("/artists/1RyvyyTE3xzB2ZywiAwp0i")
                    .then()
                    .log()
                    .all()
                    .assertThat().statusCode(200);

        }


        //Get Artist`s Albums
        @Test
        public static void getArtistAlbums(){
            ValidatableResponse response = RestAssured.given().contentType(ContentType.JSON)
                    .baseUri("https://api.spotify.com/").basePath("v1")
                    .accept(ContentType.JSON).header("Authorization", "Bearer " + accessToken)
                    .when()
                    .get("/artists/1RyvyyTE3xzB2ZywiAwp0i/albums")
                    .then()
                    .log()
                    .all()
                    .assertThat().statusCode(200);
        }



    @Test
    public static void getArtistAlbumsNegative(){
        ValidatableResponse response = RestAssured.given().contentType(ContentType.JSON)
                .baseUri("https://api.spotify.com/").basePath("v1")
                .accept(ContentType.JSON).header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/artists/1111111111111111/albums")
                .then()
                .log()
                .all()
                .assertThat().statusCode(400);
    }


    @Test
    public static void createPlaylistNegative(){

        String jsonBody = "{" +
                "   \"name\":\"MyNewPlaylist\",\n" +
                "   \"description\":\"This is the new playlist for testing framework\",\n" +
                "   \"public\":\"false\"\n" +
                "}";

        ValidatableResponse response = RestAssured.given().contentType(ContentType.JSON)
                .baseUri("https://api.spotify.com/").basePath("v1")
                .accept(ContentType.JSON).header("Authorization", "Bearer " + accessToken)
                .and()
                .body(jsonBody)
                .when()
                .post("/users/smedjan/playlists")
                .then()
                .log()
                .all()
                .assertThat().statusCode(403)
                .and()
                .statusLine("HTTP/1.1 403 Forbidden")
                .body("error.status", equalTo(403))
                .body("error.message", equalTo("You cannot create a playlist for another user."));

    }

}


