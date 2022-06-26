package ru.otus.library.configuration.router;

//@WebFluxTest({CommentRouter.class, CommentHandler.class})
//@DisplayName("Comment Router should")
//class CommentRouterTest {
//
//    @Autowired
//    private RouterFunction<ServerResponse> commentsRoute;
//    @MockBean
//    private CommentRepository commentRepository;
//    @MockBean
//    private ModelMapper modelMapper;
//    @Autowired
//    private Gson gson;
//
//    private WebTestClient client;
//
//    @BeforeEach
//    private void init() {
//        client = WebTestClient.bindToRouterFunction(commentsRoute).build();
//    }
//
//    @Test
//    void shouldReturnCorrectCommentResponse() {
//        val bookId = "bookId";
//        val uriPattern = "/api/comments/" + bookId;
//        val commentId = "commentId";
//        val book = new Book(bookId, "name", new Author("authorName"), new Genre("genreName"));
//        val comments = List.of(new Comment(commentId, "testComment", book));
//        val commentsFlux = Flux.just(comments.get(0));
//
//        val commentDto = new CommentDto();
//
//        when(commentRepository.findByBookId(bookId)).thenReturn(commentsFlux);
//
//        commentDto.setText(comments.get(0).getText());
//        commentDto.setId(comments.get(0).getId());
//        when(modelMapper.map(comments.get(0), CommentDto.class)).thenReturn(commentDto);
//        val jsonComments = gson.toJson(List.of(commentDto));
//
//        client.get()
//                .uri(uriPattern)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody().json(jsonComments);
//    }
//
//}
