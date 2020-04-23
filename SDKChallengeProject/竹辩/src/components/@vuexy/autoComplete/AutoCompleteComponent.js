import React from "react"
import ReactDOM from "react-dom"
import PropTypes from "prop-types"
import classnames from "classnames"
import { history } from "../../../history"
import PerfectScrollbar from "react-perfect-scrollbar"
import { AlertTriangle } from "react-feather"
class Autocomplete extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      activeSuggestion: 0,
      showSuggestions: false,
      userInput: "",
      focused: false,
      openUp: false
    }

    this.filteredData = []
    document.body.addEventListener("click", this.handleExtenalClick)
  }

  // Suggestion Click Event
  onSuggestionItemClick = (url, e) => {
    if (this.props.onSuggestionClick) {
      this.props.onSuggestionClick(e)
    }
    this.setState({
      activeSuggestion: 0,
      showSuggestions: false,
      userInput: e.currentTarget.innerText
    })
    if (url) history.push(url)
  }

  // Suggestion Hover Event
  onSuggestionItemHover = index => {
    this.setState({ activeSuggestion: index })
  }

  // Input Change
  onChange = e => {
    const userInput = e.currentTarget.value
    this.setState({
      activeSuggestion: 0,
      showSuggestions: true,
      userInput
    })
    if (e.target.value < 1) {
      this.setState({
        showSuggestions: false
      })
    }
  }

  // Input Click Event
  onInputClick = e => {
    e.stopPropagation()
  }

  // Input's Keydown Event
  onKeyDown = e => {
    const { activeSuggestion, showSuggestions, userInput } = this.state
    const filterKey = this.props.filterKey
    let suggestionList = ReactDOM.findDOMNode(this.suggestionList)

    // User pressed the up arrow
    if (e.keyCode === 38 && activeSuggestion !== 0) {
      this.setState({ activeSuggestion: activeSuggestion - 1 })
      if (
        e.target.value.length > -1 &&
        suggestionList !== null &&
        activeSuggestion <= this.filteredData.length / 2
      ) {
        suggestionList.scrollTop = 0
      }
    }

    // User pressed the down arrow
    else if (
      e.keyCode === 40 &&
      activeSuggestion < this.filteredData.length - 1
    ) {
      this.setState({ activeSuggestion: activeSuggestion + 1 })

      if (
        e.target.value.length > -1 &&
        suggestionList !== null &&
        activeSuggestion >= this.filteredData.length / 2
      ) {
        suggestionList.scrollTop = suggestionList.scrollHeight
      }
    }

    // User Pressed ESC
    else if (e.keyCode === 27) {
      this.setState({
        showSuggestions: false,
        userInput: ""
      })
    }

    // User Pressed ENTER
    else if (e.keyCode === 13 && showSuggestions) {
      this.onSuggestionItemClick(this.filteredData[activeSuggestion].link, e)
      this.setState({
        userInput: this.filteredData[activeSuggestion][filterKey],
        showSuggestions: false
      })
    } else {
      return
    }

    // Custom Keydown Event
    if (
      this.props.onKeyDown !== undefined &&
      this.props.onKeyDown !== null &&
      this.props.onKeyDown
    ) {
      this.props.onKeyDown(e, userInput)
    }
  }

  // Grouped Suggestions
  renderGroupedSuggestion = arr => {
    const { filterKey, customRender } = this.props
    const {
      onSuggestionItemClick,
      onSuggestionItemHover,
      state: { activeSuggestion, userInput }
    } = this

    let renderSuggestion = (item, i) => {
      if (!customRender) {
        return (
          <li
            className={classnames("suggestion-item", {
              active: this.filteredData.indexOf(item) === activeSuggestion
            })}
            key={item[filterKey]}
            onClick={e => onSuggestionItemClick(item.link, e)}
            onMouseEnter={() => {
              this.onSuggestionItemHover(this.filteredData.indexOf(item))
            }}>
            {item[filterKey]}
          </li>
        )
      } else if (customRender) {
        return customRender(
          item,
          i,
          this.filteredData,
          activeSuggestion,
          onSuggestionItemClick,
          onSuggestionItemHover,
          userInput
        )
      } else {
        return null
      }
    }

    return arr.map((item, i) => {
      return renderSuggestion(item, i)
    })
  }

  // Ungrouped Suggestions
  renderUngroupedSuggestions = () => {
    const { filterKey, suggestions, customRender, suggestionLimit } = this.props
    const {
      onSuggestionItemClick,
      onSuggestionItemHover,
      state: { activeSuggestion, userInput }
    } = this

    this.filteredData = []
    let sortSingleData = suggestions
      .filter(i => {
        let startCondition = i[filterKey]
            .toLowerCase()
            .startsWith(userInput.toLowerCase()),
          includeCondition = i[filterKey]
            .toLowerCase()
            .includes(userInput.toLowerCase())
        if (startCondition) {
          return startCondition
        } else if (!startCondition && includeCondition) {
          return includeCondition
        } else {
          return null
        }
      })
      .slice(0, suggestionLimit)
    this.filteredData.push(...sortSingleData)
    return sortSingleData.map((suggestion, index) => {
      if (!customRender) {
        return (
          <li
            className={classnames("suggestion-item", {
              active: this.filteredData.indexOf(suggestion) === activeSuggestion
            })}
            key={suggestion[filterKey]}
            onClick={e =>
              onSuggestionItemClick(suggestion.link ? suggestion.link : null, e)
            }
            onMouseEnter={() =>
              this.onSuggestionItemHover(this.filteredData.indexOf(suggestion))
            }>
            {suggestion[filterKey]}
          </li>
        )
      } else if (customRender) {
        return customRender(
          suggestion,
          index,
          this.filteredData,
          activeSuggestion,
          onSuggestionItemClick,
          onSuggestionItemHover,
          userInput
        )
      } else {
        return null
      }
    })
  }

  // Renders Suggestions
  renderSuggestions = () => {
    const { filterKey, grouped, filterHeaderKey, suggestions } = this.props
    const {
      renderUngroupedSuggestions,
      state: { userInput }
    } = this

    // Checks if suggestions are grouped or not.
    if (grouped === undefined || grouped === null || !grouped) {
      return renderUngroupedSuggestions()
    } else {
      this.filteredData = []
      return suggestions.map(suggestion => {
        let sortData = suggestion.data
          .filter(i => {
            let startCondition = i[filterKey]
                .toLowerCase()
                .startsWith(userInput.toLowerCase()),
              includeCondition = i[filterKey]
                .toLowerCase()
                .includes(userInput.toLowerCase())
            if (startCondition) {
              return startCondition
            } else if (!startCondition && includeCondition) {
              return includeCondition
            } else {
              return null
            }
          })
          .slice(0, suggestion.searchLimit)

        this.filteredData.push(...sortData)
        return (
          <React.Fragment key={suggestion[filterHeaderKey]}>
            <li className="suggestion-item suggestion-title text-primary text-bold-600">
              {suggestion[filterHeaderKey]}
            </li>
            {sortData.length ? (
              this.renderGroupedSuggestion(sortData)
            ) : (
              <li className="suggestion-item no-result">
                <AlertTriangle size={15} />{" "}
                <span className="align-middle ml-50">No Result</span>
              </li>
            )}
          </React.Fragment>
        )
      })
    }
  }

  // Clears Input
  clearInput = val => {
    if (this.props.clearInput && !val) {
      this.setState({
        userInput: ""
      })
    }
  }

  // Closes Suggestions if clicked outside container (On Blur Basically)
  handleExtenalClick = e => {
    let { container } = this.refs
    const { target } = e
    if (target !== container && !container.contains(target)) {
      this.setState({
        showSuggestions: false
      })
      if (this.props.externalClick) this.props.externalClick(e)
    }
  }

  componentDidUpdate(prevProps, prevState) {
    let textInput = ReactDOM.findDOMNode(this.input)
    let { autoFocus, onSuggestionsShown, clearInput } = this.props
    // For searchbar focus
    if (textInput !== null && autoFocus) {
      textInput.focus()
    }

    if (
      this.props.defaultSuggestions &&
      prevState.showSuggestions === false &&
      this.state.focused
    ) {
      this.setState({ showSuggestions: true })
    }

    // Clear Input
    if (clearInput === false && this.state.userInput.length) {
      this.setState({
        userInput: ""
      })
    }

    // Function on Suggestions Shown
    if (onSuggestionsShown && this.state.showSuggestions) {
      onSuggestionsShown(this.state.userInput)
    }

    if (
      this.props.defaultSuggestions &&
      prevState.focused === false &&
      this.state.focused === true
    ) {
      this.setState({ showSuggestions: true })
    }
  }

  componentDidMount() {
    if (this.props.defaultSuggestions && this.state.focused) {
      this.setState({ showSuggestions: true })
    }
  }

  componentWillUnmount() {
    document.body.removeEventListener("click", this.handleExtenalClick)
  }

  render() {
    const {
      onChange,
      onKeyDown,
      state: { showSuggestions, userInput, openUp }
    } = this
    let suggestionsListComponent

    if (showSuggestions) {
      suggestionsListComponent = (
        <PerfectScrollbar
          className={classnames("suggestions-list", {
            "open-up": openUp
          })}
          ref={el => (this.suggestionList = el)}
          component="ul"
          options={{ wheelPropagation: false }}>
          {this.renderSuggestions()}
        </PerfectScrollbar>
      )
    }

    return (
      <div className="vx-autocomplete-container" ref="container">
        <input
          type="text"
          onChange={e => {
            onChange(e)
            if (this.props.onChange) {
              this.props.onChange(e)
            }
          }}
          onKeyDown={e => onKeyDown(e)}
          value={userInput}
          className={`vx-autocomplete-search ${
            this.props.className ? this.props.className : ""
          }`}
          placeholder={this.props.placeholder}
          onClick={this.onInputClick}
          ref={el => {
            return (this.input = el)
          }}
          onFocus={e => {
            this.setState({ focused: true })
          }}
          autoFocus={this.props.autoFocus}
          onBlur={e => {
            // this.onBlur(e)
            if (this.props.onBlur) this.props.onBlur(e)
            this.setState({ focused: false })
          }}
        />
        {suggestionsListComponent}
      </div>
    )
  }
}

export default Autocomplete

Autocomplete.propTypes = {
  suggestions: PropTypes.array.isRequired,
  filterKey: PropTypes.string.isRequired,
  filterHeaderKey: PropTypes.string,
  placeholder: PropTypes.string,
  suggestionLimit: PropTypes.number,
  grouped: PropTypes.bool,
  autoFocus: PropTypes.bool,
  onKeyDown: PropTypes.func,
  onChange: PropTypes.func,
  onSuggestionsShown: PropTypes.func,
  onSuggestionItemClick: PropTypes.func
}
