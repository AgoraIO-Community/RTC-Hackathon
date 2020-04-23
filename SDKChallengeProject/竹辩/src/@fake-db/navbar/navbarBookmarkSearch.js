import mock from "../mock"

export const searchResult = [
  {
    id: 1,
    target: "home",
    title: "Home",
    link: "/",
    icon: "Home",
    starred: false
  },
  {
    id: 2,
    target: "page",
    title: "Page 2",
    link: "/page2",
    icon: "File",
    starred: false
  }
]

mock.onGet("/api/search/bookmarks/data").reply(200, {
  searchResult
})

mock.onPost("/api/update/bookmarks").reply(request => {
  const bookmarkToUpdate = JSON.parse(request.data).obj

  searchResult.filter(i => {
    if (i.id === bookmarkToUpdate.id) {
      return (i.starred = !bookmarkToUpdate.starred)
    } else {
      return null
    }
  })
  return [200]
})
