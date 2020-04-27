import mock from "../mock"

export const searchResult = [
  {
    groupTitle: "Pages",
    searchLimit: 4,
    data: [
      {
        id: 1,
        target: "home",
        title: "Home",
        link: "/",
        icon: "Home"
      },
      {
        id: 2,
        target: "page",
        title: "Page 2",
        link: "/page2",
        icon: "File"
      }
    ]
  }
]

mock.onGet("/api/main-search/data").reply(200, {
  searchResult
})
