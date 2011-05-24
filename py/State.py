#!/usr/bin/env python
# -*- coding: utf-8 -*-


class State(object):
    """ generated source for State

    """

    def getNextState(self):
        raise NotImplementedError()

    def getHost(self):
        raise NotImplementedError()

    def toString(self):
        raise NotImplementedError()

    def update(self):
        raise NotImplementedError()

    def enter(self):
        raise NotImplementedError()

    def exit(self):
        raise NotImplementedError()

    def isRunning(self):
        raise NotImplementedError()

    def getCurrentActionType(self):
        raise NotImplementedError()

    def getCurrentAction(self):
        raise NotImplementedError()


