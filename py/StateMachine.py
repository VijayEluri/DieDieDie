#!/usr/bin/env python
# -*- coding: utf-8 -*-


class StateMachine(object):
    """ generated source for StateMachine

    """

    def isFSMRunning(self):
        raise NotImplementedError()

    def getState(self):
        raise NotImplementedError()

    def createStates(self):
        raise NotImplementedError()

    def setInitialState(self):
        raise NotImplementedError()

    def startFSM(self):
        raise NotImplementedError()

    def changeState(self, nextState):
        raise NotImplementedError()

    def stopFSM(self):
        raise NotImplementedError()


