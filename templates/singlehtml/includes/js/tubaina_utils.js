(function() {
  Node.prototype.computedStyle = function(rule) {
    return window.getComputedStyle(this, null).getPropertyValue(rule);
  }

  Node.prototype.lineCount = function() {
    var originalStyle = this.getAttribute('style');
    var changedDisplayMode = false;
    var result = 0;
    // verify element display mode, make sure is 'inline'
    if (this.computedStyle('display') == 'block') {
      this.style.display = 'inline';
      changedDisplayMode = true;
    }
    // set result to client rects count;
    result = this.getClientRects().length;
    // reset original style attribute or none
    if (changedDisplayMode) {
      this.setAttribute('style', originalStyle);
    }
    return result;
  }
})();
